package com.anstech.speechtotext.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anstech.speechtotext.entity.VoiceText;
import com.anstech.speechtotext.model.Response;
import com.anstech.speechtotext.service.SpeechToTextService;

@RestController
@RequestMapping("/api/auth")
public class SpeechToTextController {

	public static final String subscriptionKey = "d42548585fd74b15a198e95fbcdfd6a4";
	public static final String regionKey = "westus";

	@Autowired
	SpeechToTextService speechToTextService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/speect-to-text/{id}")
	public Response speechToText(@PathVariable("id") Long id) {

		boolean isSpeaking = true;
		Response response = null;
		try {
			response = speechToTextService.speechToText(subscriptionKey, regionKey, isSpeaking);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/stop-recognition/{id}")
	public Response stopRecognition(@PathVariable("id") Long id) {
		Response response = null;
		try {
			boolean isSpeaking = false;
			response = speechToTextService.speechToText(subscriptionKey, regionKey, isSpeaking);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@PostMapping("/save-data")
	public ResponseEntity<?> saveVoiceText(@RequestBody VoiceText voiceText) {
		ResponseEntity<?> result = this.speechToTextService.saveVoiceText(voiceText);
		return result;
	}

	@GetMapping("/download-file/{id}")
	public ResponseEntity<?> downloadVoiceTextFile(@PathVariable("id") Long id) {
		ResponseEntity<?> result = this.speechToTextService.downloadVoiceTextFile(id);
		return result;
	}

	@PostMapping("/save-audio-file/{id}")
	public ResponseEntity<?> saveAudioFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
		ResponseEntity<?> result = null;
		try {
			result = this.speechToTextService.saveAudioText(file, id);
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		}
		return result;
	}
}
