package com.tivo.test.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.test.kafka.dto.Address;
import com.tivo.test.kafka.dto.UserBasic;
import com.tivo.test.kafka.dto.UserDetails;
import com.tivo.test.kafka.dto.UserMessage;

public class UserDetailSerde implements Serde<UserDetails> {

	final private Serializer<UserDetails> serializer;
	final private Deserializer<UserDetails> deserializer;

	private class BasicSeralization implements Serializer<UserDetails> {

		@Override
		public byte[] serialize(String topic, UserDetails data) {
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

	private class BasicDeserialization implements Deserializer<UserDetails> {

		@Override
		public UserDetails deserialize(String topic, byte[] data) {
			ObjectMapper mapper = new ObjectMapper();
			UserDetails userDetail = null;
			try {
				JsonNode detailNode = mapper.readTree(data);
				JsonNode basicNode = detailNode.get("userBasic");
				UserBasic basic = new UserBasic(basicNode.get("id").asLong(), basicNode.get("userName").asText(),
						new Address(basicNode.get("address").get("zipCode").asInt()));
				
				JsonNode messageNode = detailNode.get("userMessage");
				UserMessage message = mapper.treeToValue(messageNode, UserMessage.class);
				UserDetails details = new UserDetails(basic, message);
				//userDetail = mapper.readValue(data, UserDetails.class);
				userDetail = details;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userDetail;
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
