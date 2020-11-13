package com.tivo.test.kafka.configure;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tivo.test.kafka.dto.UserBasic;
import com.tivo.test.kafka.dto.UserMessage;
import com.tivo.test.kafka.serialization.UserBasicSerde;
import com.tivo.test.kafka.serialization.UserMessageSerde;

@Configuration
public class KafkaStreamConfig {

	@Value("${message.basic-topic}")
	private String basicTopic;

	@Value("${message.message-topic}")
	private String messageTopic;

	@Bean
	public Properties buildStreamProperty() {
		Properties properties = new Properties();

		return properties;
	}

	@Bean
	public StreamsBuilder getBuildStream() {
		StreamsBuilder builder = new StreamsBuilder();
		return builder;
	}

	@Bean(name =  "userBasicKTable")
	public KTable<String, UserBasic> userBasicKTable() {
		StreamsBuilder builder = getBuildStream();
		KTable<String, UserBasic> userBasicData = builder.table(basicTopic,
				Consumed.with(Serdes.String(), new UserBasicSerde()));
		return userBasicData;
	}

	@Bean(name = "userMessageKTable")
	public KTable<String, UserMessage> userMessageKTable() {
		StreamsBuilder builder = getBuildStream();
		KTable<String, UserMessage> messageTable = builder.table(messageTopic,
				Consumed.with(Serdes.String(), new UserMessageSerde()));
		return messageTable;
	}
}
