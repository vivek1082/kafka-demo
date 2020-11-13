package com.tivo.test.kafka.configure;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tivo.test.kafka.dto.UserBasic;
import com.tivo.test.kafka.dto.UserMessage;
import com.tivo.test.kafka.serialization.UserBasicSerde;
import com.tivo.test.kafka.serialization.UserDetailSerde;
import com.tivo.test.kafka.serialization.UserMessageSerde;
import com.tivo.test.kafka.utility.UserDetailsJoiner;

@Configuration
public class KafkaStreamConfig {

	@Value("${message.basic-topic}")
	private String basicTopic;

	@Value("${message.message-topic}")
	private String messageTopic;

	@Value("${message.detail-topic}")
	private String userDetails;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String appName;

	@Bean
	public Properties buildStreamProperty() {
		Properties properties = new Properties();
		
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG,appName );
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return properties;
	}

	@Bean
	public StreamsBuilder getBuildStream() {
		StreamsBuilder builder = new StreamsBuilder();

		userMsgCombiner(builder);
		return builder;
	}

	@Bean
	public Topology buildTopology() {
		StreamsBuilder builder = getBuildStream();
		return builder.build();
	}

	@Bean(name = "msgCombinerStream")
	public KafkaStreams msgmsgCombinerStream() {
		KafkaStreams kafkaStreams = new KafkaStreams(buildTopology(), buildStreamProperty());

		return kafkaStreams;
	}

	private void userMsgCombiner(StreamsBuilder builder) {
		// stream user-basic
		KTable<String, UserBasic> userBasicKTable = builder.table(basicTopic,
				Consumed.with(Serdes.String(), new UserBasicSerde()));

		// stream user-msg
		KTable<String, UserMessage> userMessageKTable = builder.table(messageTopic,
				Consumed.with(Serdes.String(), new UserMessageSerde()));

		// combine msg
		UserDetailsJoiner detailsJoiner = new UserDetailsJoiner();
		userBasicKTable.join(userMessageKTable, detailsJoiner).toStream().to(userDetails,
				Produced.with(Serdes.String(), new UserDetailSerde()));

	}

}
