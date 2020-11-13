package com.tivo.test.kafka.dto;

import java.io.Serializable;


public class UserId implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public UserId() {
		super();
	}
	
	public UserId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserId [id=" + id + "]";
	}
	
}
