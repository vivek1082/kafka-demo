package com.tivo.test.kafka.runnable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.tivo.test.kafka.dto.Address;
import com.tivo.test.kafka.dto.UserBasic;

@Component
public class UserBasicThread implements Runnable {

	@Autowired
	private KafkaTemplate<String, UserBasic> userBasic;

	@Value("${message.basic-topic}")
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

		ListenableFuture<SendResult<String, UserBasic>> future = userBasic.send(topicName,
				new UserBasic((long) id, "test" + id, new Address(Integer.parseInt("0000" + id))));

		future.addCallback(new ListenableFutureCallback<SendResult<String, UserBasic>>() {

			@Override
			public void onSuccess(SendResult<String, UserBasic> result) {
				System.out.println("Added user ::" + result.getProducerRecord().value().getUserName());

			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Send Failed::" + ex.getMessage());

			}
		});

	}

}
