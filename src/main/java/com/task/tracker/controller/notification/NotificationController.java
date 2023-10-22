package com.task.tracker.controller.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Locale;

@Controller
public class NotificationController {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/ws")
	public String send(final String message) throws Exception {
		return message.toUpperCase();
	}
}
