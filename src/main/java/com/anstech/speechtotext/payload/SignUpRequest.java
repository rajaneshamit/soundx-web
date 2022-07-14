package com.anstech.speechtotext.payload;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

	@NotBlank
	// @Size(max = 40)
	private String firstName;

	@NotBlank
	// @Size(max = 15)
	private String lastName;

	@NotBlank
	// @Size(max = 200)
	private String mobile;

	@NotBlank
	// @Size(max = 40)
	@Email
	private String email;

	@NotBlank
	// @Size(max = 100)
	private String password;

	// private String address; // for agent only

	@Column(name = "active")
	private String active;

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
		return "SignUpRequest [firstName=" + firstName + ", lastName=" + lastName + ", mobile=" + mobile + ", email="
				+ email + ", password=" + password + ", active=" + active + "]";
	}

	

}
