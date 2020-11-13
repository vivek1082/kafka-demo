package com.tivo.test.kafka.dto;

import java.io.Serializable;


public class UserBasic extends UserId implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private Address address;
	
	public UserBasic() {
		super();
	}
	
	public UserBasic(Long id,String userName,Address address) {
		super(id);
		this.address = address;
		this.userName = userName;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "UserBasic [userName=" + userName + ", address=" + address + "]";
	}

	
	
	

}
