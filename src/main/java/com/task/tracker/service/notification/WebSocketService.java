package com.task.tracker.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	public void sendMessage(final String topic, final String message){
		simpMessagingTemplate.convertAndSend(topic, message);
	}
}
