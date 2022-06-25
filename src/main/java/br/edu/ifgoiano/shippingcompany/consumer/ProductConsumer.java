package br.edu.ifgoiano.shippingcompany.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifgoiano.shippingcompany.service.RabbitMQService;
import constants.RabbitMQConstants;
import dto.ProductDto;

@Component
public class ProductConsumer {

	@Autowired
	private RabbitMQService rabbitMQService;

	@RabbitListener(queues = RabbitMQConstants.QUEUE_SEND_PRODUCTS)
	private void consumer(ProductDto productDto) {
		System.out.println(productDto.id + ", " + productDto.name + ", " + productDto.sender + ", "
				+ productDto.receiver + ", " + productDto.weight);
		if (productDto.weight > 10.0)
			this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_HEAVY_PRODUCTS, productDto);
		else
			this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_LIGHT_PRODUCST, productDto);
	}

}
