package com.anstech.speechtotext.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.anstech.speechtotext.entity.User;
import com.anstech.speechtotext.entity.VoiceText;
import com.anstech.speechtotext.enums.SpeechTextType;
import com.anstech.speechtotext.helper.AudioToTextUtil;
import com.anstech.speechtotext.helper.ResponseUtil;
import com.anstech.speechtotext.model.Response;
import com.anstech.speechtotext.repo.SpeechToTextRepository;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class SpeechToTextServiceImpl implements SpeechToTextService {

	private static Semaphore stopTranslationWithFileSemaphore;
	private SpeechRecognizer speechRecognizer;
	private SpeechConfig speechConfig;
	private AudioConfig audioConfig;

	@Autowired
	private SpeechToTextRepository speechToTextRepository;
	@Autowired
	private FileExporterService fileExporterService;
	
	@Value("${filepath}")
	private String filePath;

	@Override
	public Response speechToText(String subScriptionKey, String regionKey, boolean isSpeaking)
			throws InterruptedException, ExecutionException {
		speechConfig = SpeechConfig.fromSubscription(subScriptionKey, regionKey);
		speechConfig.setSpeechRecognitionLanguage("en-US");
		speechConfig.enableDictation();
		// TODO Auto-generated method stub
		return recognizeFromMicrophone(speechConfig, isSpeaking);
	}

	public Response recognizeFromMicrophone(SpeechConfig speechConfig, boolean isSpeaking)
			throws InterruptedException, ExecutionException {
		Response response = new Response();
		if (audioConfig == null) {
			audioConfig = AudioConfig.fromDefaultMicrophoneInput();
		}
		System.out.println("speechRecognizer ::" + speechRecognizer);
		// pick a conversation Id that is a GUID.
		String conversationId = UUID.randomUUID().toString();
		System.out.println("uuid ::"+conversationId);
		if (speechRecognizer == null) {
			System.out.println("insideee@@@@@@ null to create session");
			speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
		}
		stopTranslationWithFileSemaphore = new Semaphore(0);
		System.out.println("Speak into your microphone. " + isSpeaking);
		if (isSpeaking) {
			speechRecognizer.recognized.addEventListener((s, e) -> {
				if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
					response.setMessage("Success");
					response.setStatusCode("200");
					response.setDescription(e.getResult().getText());
					System.out.println(Thread.currentThread().getName() + ": " + e.getResult().getText());
					writeData(e.getResult().getText(), isSpeaking,conversationId);
					if (e.getResult().getText().equalsIgnoreCase("Thank you.")) {
						System.out.println("inside thank you");
						speechRecognizer.close();
					}

				} else if (e.getResult().getReason() == ResultReason.NoMatch) {
					System.out.println("NOMATCH: Speech could not be recognized.");
				}
			});

		} else {
			System.out.println("into else part to stop the session");
			// 9speechRecognizer.close();
			speechRecognizer.sessionStopped.addEventListener((s, e) -> {
				System.out.println("\n    Session stopped event.");
				stopTranslationWithFileSemaphore.release();
			});
		}
		speechRecognizer.startContinuousRecognitionAsync().get();
		if (!isSpeaking) {
			speechRecognizer.stopContinuousRecognitionAsync().get();
			speechRecognizer.close();
			speechRecognizer = null;
			audioConfig = null;
//			File file = new File("D://home//projects//speechtotext//file.txt");
//			file.delete();
//			if (!file.exists()) {
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}
		response.setMessage("Success");
		response.setDescription(conversationId);
		response.setStatusCode("200");
		return response;

		// System.exit(0);
	}

	private void writeData(String data, boolean isSpeaking,String conversationId) {
		File file = new File(filePath+conversationId+".txt");
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			if (isSpeaking) {
				FileWriter fileWriter = new FileWriter(file, true);

				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(" " + data + " " + " ");
				bufferedWriter.close();
			}
			System.out.println("Done");
		} catch (IOException e) {
			System.out.println("exception ::" + e.getMessage());
			System.out.println("COULD NOT LOG!!");
		}
	}

	@Override
	public ResponseEntity<?> saveVoiceText(VoiceText voiceText) {
		voiceText.setType(SpeechTextType.SPEECH_TO_TEXT);
		VoiceText save = this.speechToTextRepository.save(voiceText);
		return ResponseEntity.ok(new ResponseUtil("insert successfully", "success"));
	}

	@Override
	public ResponseEntity<?> downloadVoiceTextFile(Long id) {
		VoiceText data = this.speechToTextRepository.findById(id).get();
		String logFileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
		logFileName = data.getUser().getFirstName() + logFileName;
		try {
			Path exportedPath = fileExporterService.export(data.getContent(), logFileName);
			File exportedFile = exportedPath.toFile();
			FileInputStream fileInputStream = new FileInputStream(exportedFile);
			InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + logFileName)
					.contentType(MediaType.TEXT_PLAIN).contentLength(exportedFile.length()).body(inputStreamResource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(new ResponseUtil("Something went wrong", "fail"));
	}

	@Override
	public ResponseEntity<?> saveAudioText(MultipartFile file, Long id)
			throws InterruptedException, ExecutionException, IOException {
		AudioToTextUtil audioToTextUtil = new AudioToTextUtil();
		String result = audioToTextUtil.saveAudio(file, id);
		VoiceText voiceData = new VoiceText();
		User user = new User();
		user.setId(id);
		voiceData.setContent(result);
		voiceData.setType(SpeechTextType.AUDIO_TO_TEXT);
		voiceData.setUser(user);
		this.speechToTextRepository.save(voiceData);
		return ResponseEntity.ok(new ResponseUtil(result, "success"));
	}		
}
