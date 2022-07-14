package com.anstech.speechtotext.service;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;

import com.anstech.speechtotext.entity.VoiceText;
import com.anstech.speechtotext.model.Response;

public interface SpeechToTextService {

	public Response speechToText(String subscriptionKey, String regionKey, boolean isSpeaking)
			throws InterruptedException, ExecutionException;

	ResponseEntity<?> saveVoiceText(VoiceText voiceText);

	ResponseEntity<?> downloadVoiceTextFile(Long id);

}
