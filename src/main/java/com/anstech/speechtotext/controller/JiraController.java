package com.anstech.speechtotext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anstech.speechtotext.entity.Test;
import com.anstech.speechtotext.service.JiraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/auth")
public class JiraController {

	@Autowired
	private JiraService jiraService;

	@PostMapping("/create-jira-issue")
	public ResponseEntity<?> createJiraIssue(@RequestParam("file") MultipartFile file,
			@RequestParam("test") String test) {
		Test testObject = null;
		try {
			testObject = new ObjectMapper().readValue(test, Test.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(testObject);
		ResponseEntity<?> creaetJiraIssue = this.jiraService.creaetJiraIssue(testObject);
		return creaetJiraIssue;
	}
}