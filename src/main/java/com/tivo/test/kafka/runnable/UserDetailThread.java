package com.tivo.test.kafka.runnable;

import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tivo.test.kafka.dto.UserBasic;
import com.tivo.test.kafka.dto.UserMessage;

@Component
public class UserDetailThread implements Runnable {

	@Value("${message.detail-topic}")
	private String userDetails;
	
	@Autowired
	private KTable<String,UserBasic> userBasicKTable;
	
	@Autowired
	private KTable<String, UserMessage> userMessageKTable;
	
	@Override
	public void run() {
//		userBasicKTable.join(userMessageKTable, )

	}

}
