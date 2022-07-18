package com.anstech.speechtotext.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.anstech.speechtotext.entity.VoiceText;
import com.anstech.speechtotext.model.Response;

public interface SpeechToTextService {

	public Response speechToText(String subscriptionKey, String regionKey, boolean isSpeaking)
			throws InterruptedException, ExecutionException;

	ResponseEntity<?> saveVoiceText(VoiceText voiceText);

	ResponseEntity<?> downloadVoiceTextFile(Long id);

	ResponseEntity<?> saveAudioText(MultipartFile file, Long id) throws InterruptedException, ExecutionException, IOException;

}
