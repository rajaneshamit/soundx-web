package com.anstech.speechtotext.service;

import org.springframework.http.ResponseEntity;

import com.anstech.speechtotext.entity.Test;

public interface JiraService {

	ResponseEntity<?> creaetJiraIssue(Test  fieldsModel);

}
