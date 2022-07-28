package com.anstech.speechtotext.payload;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class LoginRequest {
    @NotBlank
    private String mobileOrEmail;

    @NotBlank
    private String password;
    

    public String getMobileOrEmail() {
		return mobileOrEmail;
	}

	public void setMobileOrEmail(String mobileOrEmail) {
		this.mobileOrEmail = mobileOrEmail;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
	@Override
	public String toString() {	
		String str = "";
		
		try {
			str = new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}	
}
