package com.abw12.springbootawssqsexample;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootAwsSqsExampleApplication {

	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	Logger logger = LoggerFactory.getLogger(SpringbootAwsSqsExampleApplication.class);


	@GetMapping("/sendMessage/{message}")
	public void sendMessageToQueue(@PathVariable String message){
		queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
	}

	@SqsListener("springboot-sqs-example")
	public void loadMessageFromSQSQueue(String message){
		logger.info("Message From SQS Queue: {}",message);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAwsSqsExampleApplication.class, args);
	}

}
