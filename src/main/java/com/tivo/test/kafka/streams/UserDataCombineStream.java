package com.tivo.test.kafka.streams;

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tivo.test.kafka.runnable.UserDetailThread;

@Component
public class UserDataCombineStream {

	@Autowired
	private ExecutorService cachedThreadPool;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void userDataCombineStart() {
		UserDetailThread detailThread = applicationContext.getBean(UserDetailThread.class);
		cachedThreadPool.execute(detailThread);
	}
}
