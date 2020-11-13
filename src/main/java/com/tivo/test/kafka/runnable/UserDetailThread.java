package com.tivo.test.kafka.runnable;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDetailThread implements Runnable {

	@Autowired
	private KafkaStreams msgCombinerStream;

	@Override
	public void run() {
		// start streaming the combined msg to topics
		msgCombinerStream.start();

	}

}
