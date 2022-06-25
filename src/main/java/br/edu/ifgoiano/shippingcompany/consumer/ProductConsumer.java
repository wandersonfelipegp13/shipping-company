package br.edu.ifgoiano.shippingcompany.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifgoiano.shippingcompany.service.RabbitMQService;
import constants.RabbitMQConstants;
import dto.ProductDto;
import dto.ReportDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductConsumer {

	@Autowired
	private RabbitMQService rabbitMQService;

	@RabbitListener(queues = RabbitMQConstants.QUEUE_SEND_PRODUCTS)
	private void consumeSendProducts(String message) throws JsonMappingException, JsonProcessingException {
		ProductDto productDto = new ObjectMapper().readValue(message, ProductDto.class);
		
		System.out.println(productDto.id + ", " + productDto.name + ", " + productDto.sender + ", "
				+ productDto.receiver + ", " + productDto.weight);
		
		if (productDto.weight > 10.0)
			this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_HEAVY_PRODUCTS, productDto);
		else
			this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_LIGHT_PRODUCST, productDto);
	}
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_RESPONSE)
	private void consumeResponse(String message) throws JsonMappingException, JsonProcessingException {
		ReportDto reportDto = new ObjectMapper().readValue(message, ReportDto.class);
		
		System.out.println(reportDto.id + ", " + reportDto.productId + ", " + reportDto.enterprise + ", " + reportDto.deliveryDate);
		
		this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_REPORT, reportDto);
	}

}
