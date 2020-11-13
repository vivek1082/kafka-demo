package com.tivo.test.kafka.dto;

import java.io.Serializable;

public class Address implements Serializable {

	
	private static final long serialVersionUID = 2101381221857221270L;
	
	//keeping the bare minimum detail
	private Integer zipCode;
	
	public Address(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address [zipCode=" + zipCode + "]";
	}

	
}
