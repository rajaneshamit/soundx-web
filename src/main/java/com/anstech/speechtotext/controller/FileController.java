package com.anstech.speechtotext.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anstech.speechtotext.model.Response;
import com.anstech.speechtotext.service.FileReaderService;

@RestController
@RequestMapping("/api/auth")
public class FileController {

	@Autowired
	FileReaderService readerService;
    Logger logger = LoggerFactory.getLogger(FileController.class);


	@GetMapping("read-text/{fileName}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Response readFileData(@PathVariable("fileName") String fileName) {
		Response response = null;
		try {
			response = readerService.readFileData(fileName);
		} catch (IOException e) {
			logger.info("Cause :: "+e);
			response = new Response();
			response.setMessage("Fail");
			response.setDescription("Invalid file name "+e.getMessage());
			response.setStatusCode("200");
		}
		return response;
	}
}
