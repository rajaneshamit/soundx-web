package com.anstech.speechtotext.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anstech.speechtotext.model.Response;
import com.anstech.speechtotext.service.FileReaderService;

@RestController
@RequestMapping("/rest/api")
public class FileController {
	
	@Autowired
	FileReaderService readerService;
	
	@GetMapping("read-text")
	@CrossOrigin(origins = "http://localhost:4200")
	public Response readFileData() {
		Response response = null;
		try {
			response = readerService.readFileData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
