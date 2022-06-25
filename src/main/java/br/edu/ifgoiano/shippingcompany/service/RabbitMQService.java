package br.edu.ifgoiano.shippingcompany.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
		
	public void sendMessage(String queueName, Object message) {
		try {
			String jsonMessage = new ObjectMapper().writeValueAsString(message);	
			this.rabbitTemplate.convertAndSend(queueName, jsonMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
