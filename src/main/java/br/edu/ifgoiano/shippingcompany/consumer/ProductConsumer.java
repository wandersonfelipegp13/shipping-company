package br.edu.ifgoiano.shippingcompany.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import constants.RabbitMQConstants;
import dto.ProductDto;

@Component
public class ProductConsumer {

	@RabbitListener(queues = RabbitMQConstants.QUEUE_SEND_PRODUCTS)
	private void consumer(ProductDto productDto) {
		System.out.println(productDto.id + ", " + productDto.name + ", " + productDto.sender + ", "
				+ productDto.receiver + ", " + productDto.weight);
	}

}
