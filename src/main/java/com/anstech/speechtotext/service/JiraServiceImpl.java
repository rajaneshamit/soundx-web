package com.anstech.speechtotext.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.anstech.speechtotext.entity.JiraFieldsEntity;
import com.anstech.speechtotext.entity.Test;
import com.anstech.speechtotext.model.JiraFieldsModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JiraServiceImpl implements JiraService {

	private final RestTemplate restTemplate;

	@Autowired
	public JiraServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		super();
		this.restTemplate = restTemplateBuilder.build();
	}

	List<JiraFieldsEntity> entities = new ArrayList<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<?> creaetJiraIssue(Test test1) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YW5rdXJzaGFreWExNDVAZ21haWwuY29tOkpUNUdEZ3R4TjJ0NUhYYThVRkFCMDFEMg==");
		headers.add("Content-Type", "application/json");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(test1,headers);
		ResponseEntity<?> exchange = this.restTemplate.exchange("https://anstechdemo.atlassian.net/rest/api/2/issue",
				HttpMethod.POST, httpEntity, String.class);
		return new ResponseEntity(exchange, HttpStatus.CREATED);
	}
}
