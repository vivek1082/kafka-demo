package com.tivo.test.kafka.streams;

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tivo.test.kafka.runnable.UserBasicThread;
import com.tivo.test.kafka.runnable.UserMessageThread;

@Component
public class UserProducer {
	
	@Autowired
	private ExecutorService cachedThreadPool;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void startUserBasicMessage() {
		//start producing basic user and message in beginning
		sendUserBasic();
		sendUserMessage();
		
	}
	
	private void sendUserBasic() {
		UserBasicThread basicThread = applicationContext.getBean(UserBasicThread.class);
		cachedThreadPool.execute(basicThread);
	}
	
	private void sendUserMessage() {
		UserMessageThread messageThread = applicationContext.getBean(UserMessageThread.class);
		cachedThreadPool.execute(messageThread);
	}

}
