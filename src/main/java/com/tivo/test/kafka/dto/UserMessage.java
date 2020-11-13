package com.tivo.test.kafka.dto;

import java.io.Serializable;

public class UserMessage extends UserId implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public UserMessage() {
		super();
	}
	
	public UserMessage(Long id, String message) {
		super(id);
		this.message= message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserMessage [message=" + message + "]";
	}
	
	
	
	

}
