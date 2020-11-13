package com.tivo.test.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.test.kafka.dto.Address;
import com.tivo.test.kafka.dto.UserBasic;

public class UserBasicSerde implements Serde<UserBasic> {
	final private Serializer<UserBasic> serializer;
	final private Deserializer<UserBasic> deserializer;

	private class BasicSeralization implements Serializer<UserBasic> {

		@Override
		public byte[] serialize(String topic, UserBasic data) {
			byte[] retVal = null;
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enableDefaultTyping();
			try {
				retVal = objectMapper.writeValueAsString(data).getBytes();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retVal;
		}

	}

	private class BasicDeserialization implements Deserializer<UserBasic> {

		@Override
		public UserBasic deserialize(String topic, byte[] data) {
			ObjectMapper mapper = new ObjectMapper();
			UserBasic userBasic = null;
			try {
				JsonNode userNode = mapper.readTree(data);

				Long long1 = userNode.get("id").asLong();
				UserBasic basic = new UserBasic(userNode.get("id").asLong(), userNode.get("userName").asText(),
						new Address(userNode.get("address").get("zipCode").asInt()));

				userBasic = basic;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userBasic;
		}
	}

	public UserBasicSerde() {
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
	public Serializer<UserBasic> serializer() {
		return serializer;
	}

	@Override
	public Deserializer<UserBasic> deserializer() {
		return deserializer;
	}
}
