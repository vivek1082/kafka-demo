package com.tivo.test.kafka.dto;

import java.io.Serializable;

public class UserDetails extends UserId implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserBasic userBasic;

	private UserMessage userMessage;

	public UserDetails() {
	}

	public UserDetails(UserBasic userBasic, UserMessage userMessage) {
		super(userBasic.getId());
		this.userBasic = userBasic;
		this.userMessage = userMessage;
	}

	public UserBasic getUserBasic() {
		return userBasic;
	}

	public void setUserBasic(UserBasic userBasic) {
		this.userBasic = userBasic;
	}

	public UserMessage getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(UserMessage userMessage) {
		this.userMessage = userMessage;
	}

	@Override
	public String toString() {
		return "UserDetails [userBasic=" + userBasic + ", userMessage=" + userMessage + "]";
	}

	public static UserDetails newBuilder() {
		return new UserDetails();

	}

}
