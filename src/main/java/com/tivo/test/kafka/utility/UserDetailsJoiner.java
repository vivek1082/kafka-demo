package com.tivo.test.kafka.utility;

import org.apache.kafka.streams.kstream.ValueJoiner;

import com.tivo.test.kafka.dto.UserBasic;
import com.tivo.test.kafka.dto.UserDetails;
import com.tivo.test.kafka.dto.UserMessage;

public class UserDetailsJoiner implements ValueJoiner<UserBasic, UserMessage, UserDetails> {

	@Override
	public UserDetails apply(UserBasic basic, UserMessage message) {

		UserDetails details = UserDetails.newBuilder();
		details.setId(basic.getId());
		details.setUserBasic(basic);
		details.setUserMessage(message);
		return details;
	}

}
