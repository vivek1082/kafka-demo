package com.tivo.test.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.test.kafka.dto.UserDetails;

public class UserDetailSerde implements Serde<UserDetails> {

	final private Serializer<UserDetails> serializer;
	final private Deserializer<UserDetails> deserializer;

	private class BasicSeralization implements Serializer<UserDetails> {

		@Override
		public byte[] serialize(String topic, UserDetails data) {
			byte[] retVal = null;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				retVal = objectMapper.writeValueAsString(data).getBytes();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retVal;
		}

	}

	private class BasicDeserialization implements Deserializer<UserDetails> {

		@Override
		public UserDetails deserialize(String topic, byte[] data) {
			ObjectMapper mapper = new ObjectMapper();
			UserDetails userBasic = null;
			try {
				userBasic = mapper.readValue(data, UserDetails.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userBasic;
		}
	}

	public UserDetailSerde() {
		this.serializer = new BasicSeralization();
		this.deserializer = new BasicDeserialization();
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		serializer.configure(configs, isKey);
		deserializer.configure(configs, isKey);
	}

	@Override
	public void close() {
		serializer.close();
		deserializer.close();
	}

	@Override
	public Serializer<UserDetails> serializer() {
		return serializer;
	}

	@Override
	public Deserializer<UserDetails> deserializer() {
		return deserializer;
	}

}
