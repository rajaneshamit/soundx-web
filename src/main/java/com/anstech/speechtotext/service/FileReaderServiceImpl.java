package com.anstech.speechtotext.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anstech.speechtotext.model.Response;

@Service
public class FileReaderServiceImpl implements FileReaderService {
	
	@Value("${filepath}")
	private String filePath;
	

	@Override
	public Response readFileData(String fileName) throws IOException {
		Response response = new Response();
		File file = new File(filePath+fileName+".txt");
		String text = new String(Files.readAllBytes(file.toPath()));
		if (text != "") {
			response.setDescription(text);
			response.setMessage("Success");
			response.setStatusCode("200");
		}
		return response;
	}
}
