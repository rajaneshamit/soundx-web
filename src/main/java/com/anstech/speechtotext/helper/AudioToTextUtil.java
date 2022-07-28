package com.anstech.speechtotext.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.anstech.speechtotext.entity.User;
import com.anstech.speechtotext.entity.VoiceText;
import com.anstech.speechtotext.enums.SpeechTextType;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

public class AudioToTextUtil {
	@Value("${audiofilepath}")
	private String audioFilePath;
	public static final String subscriptionKey = "22684546d3bd43b89942fe1cf982148c";
	public static final String regionKey = "westus";

	public String saveAudio(MultipartFile file, Long id) throws InterruptedException, ExecutionException, IOException {
		SpeechConfig speechConfig = SpeechConfig.fromSubscription(subscriptionKey, regionKey);
		String filePath = saveFile(file).substring(1);
		AudioConfig audioConfig = AudioConfig.fromWavFileInput(filePath);
		SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
		Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
		SpeechRecognitionResult result = task.get();
		return result.getText();

	}

	private String saveFile(MultipartFile file) {
		Path path = null;
		try {
			String fileName = file.getOriginalFilename();
			if (fileName.contains("..")) {
				System.out.println("Invalid file");
			}
			path = Paths.get(audioFilePath + fileName);

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path.toUri().getPath();
	}

}
