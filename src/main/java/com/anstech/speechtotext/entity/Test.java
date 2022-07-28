package com.anstech.speechtotext.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	private JiraFieldsEntity fields;

	public JiraFieldsEntity getFields() {
		return fields;
	}

	public void setFields(JiraFieldsEntity fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		String str = null;

		try {
			str = new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
