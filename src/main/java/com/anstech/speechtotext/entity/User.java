package com.anstech.speechtotext.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.anstech.speechtotext.audit.Auditable;

@Entity
@Table(name = "user_details")
public class User extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String mobile;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@Column(name = "active")
	private String active;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<VoiceText> voiceText;

	// @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
	// inverseJoinColumns = @JoinColumn(name = "role_id"))
	// private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String firstName, String lastName, String email, String password, String mobile, String active) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.active = active;
	}

	public User(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String mobile,
			@NotBlank @Email String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile=" + mobile
				+ ", email=" + email + ", password=" + password + ", active=" + active + "]";
	}

}
