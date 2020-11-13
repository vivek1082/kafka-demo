package com.tivo.test.kafka.runnable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.tivo.test.kafka.dto.UserMessage;

@Component
public class UserMessageThread implements Runnable {

	@Autowired
	private KafkaTemplate<String, UserMessage> userMessage;

	@Value("${message.message-topic}")
	private String topicName;

	@Override
	public void run() {
		int startIndex = 0;
		while (true) {
			try {
				sendMessage(++startIndex);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(int id) {

		ListenableFuture<SendResult<String, UserMessage>> future = userMessage.send(topicName,
				new UserMessage((long) id, "message" + id));

		future.addCallback(new ListenableFutureCallback<SendResult<String, UserMessage>>() {

			@Override
			public void onSuccess(SendResult<String, UserMessage> result) {
				System.out.println("Added message ::" + result.getProducerRecord().value().getMessage());

			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Send Failed::" + ex.getMessage());

			}
		});

	}

}
