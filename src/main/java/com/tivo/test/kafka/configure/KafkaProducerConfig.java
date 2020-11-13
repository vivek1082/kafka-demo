package com.tivo.test.kafka.configure;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.tivo.test.kafka.dto.UserBasic;
import com.tivo.test.kafka.dto.UserDetails;
import com.tivo.test.kafka.dto.UserMessage;

/**
 * 
 * @author Vivek 3 topic needs to be written
 *
 */
@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public Map<String, Object> producerConfig() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(
		          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
		          bootstrapAddress);
		        properties.put(
		          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
		          StringSerializer.class);
		        

		return properties;
	}

	@Bean
	public ProducerFactory<String, UserBasic> userBasicProducerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig(),null,new JsonSerializer<UserBasic>());
	}

	@Bean
	public ProducerFactory<String, UserMessage> userMessageProducerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig(),null,new JsonSerializer<UserMessage>());
	}

	@Bean
	public ProducerFactory<String, UserDetails> userDetailProducerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig(),null,new JsonSerializer<UserDetails>());
	}
	
	@Bean(name = "userBasic")
	public KafkaTemplate<String,UserBasic> basicTemplate() {
		return new KafkaTemplate<String, UserBasic>(userBasicProducerFactory());
	}
	
	@Bean(name = "userMessage")
	public KafkaTemplate<String,UserMessage> messageTemplate() {
		return new KafkaTemplate<String, UserMessage>(userMessageProducerFactory());
	}
	
	@Bean(name = "userDetail")
	public KafkaTemplate<String,UserDetails> detailTemplate() {
		return new KafkaTemplate<String, UserDetails>(userDetailProducerFactory());
	}
	
	

}
