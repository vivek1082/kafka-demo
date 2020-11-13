package com.tivo.test.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.test.kafka.dto.UserMessage;

public class UserMessageSerde implements Serde<UserMessage> {

	final private Serializer<UserMessage> serializer;
	final private Deserializer<UserMessage> deserializer;

	private class BasicSeralization implements Serializer<UserMessage> {

		@Override
		public byte[] serialize(String topic, UserMessage data) {
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

	private class BasicDeserialization implements Deserializer<UserMessage> {

		@Override
		public UserMessage deserialize(String topic, byte[] data) {
			ObjectMapper mapper = new ObjectMapper();
			UserMessage userBasic = null;
			try {
				userBasic = mapper.readValue(data, UserMessage.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userBasic;
		}
	}

	public UserMessageSerde() {
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
	public Serializer<UserMessage> serializer() {
		return serializer;
	}

	@Override
	public Deserializer<UserMessage> deserializer() {
		return deserializer;
	}

}
