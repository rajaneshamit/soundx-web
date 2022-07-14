package com.anstech.speechtotext.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.anstech.speechtotext.model.Response;

public interface FileReaderService {

	public Response readFileData() throws IOException;
}
