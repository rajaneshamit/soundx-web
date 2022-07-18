package com.anstech.speechtotext.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.anstech.speechtotext.enums.SpeechTextType;

@Table(name = "voice_data")
@Entity
public class VoiceText {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 5000)
	@NotBlank
	private String content;

	private SpeechTextType type;

	@ManyToOne
	private User user;

	public VoiceText() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoiceText(Long id, @NotBlank String content, @NotBlank SpeechTextType type, User user) {
		super();
		this.id = id;
		this.content = content;
		this.type = type;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SpeechTextType getType() {
		return type;
	}

	public void setType(SpeechTextType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "VoiceText [id=" + id + ", content=" + content + ", type=" + type + ", user=" + user + "]";
	}
}
