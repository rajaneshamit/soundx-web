package com.anstech.speechtotext.model;

import javax.persistence.Id;

import com.anstech.speechtotext.entity.IssueType;
import com.anstech.speechtotext.entity.Project;

public class JiraFieldsModel {

	private long jiraId;
	private Project project;
	private String summary;
	private IssueType issuetype;
	private String description;

	public JiraFieldsModel(long jiraId, Project project, String summary, IssueType issuetype, String description) {
		super();
		this.jiraId = jiraId;
		this.project = project;
		this.summary = summary;
		this.issuetype = issuetype;
		this.description = description;
	}

	public JiraFieldsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getJiraId() {
		return jiraId;
	}

	public void setJiraId(long jiraId) {
		this.jiraId = jiraId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public IssueType getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(IssueType issuetype) {
		this.issuetype = issuetype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
