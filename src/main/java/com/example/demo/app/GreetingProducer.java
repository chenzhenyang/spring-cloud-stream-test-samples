package com.example.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableBinding(Source.class)
public class GreetingProducer {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Source source;

	public void sayHello(String name) throws JsonProcessingException {
		Greeting greeting = new Greeting();
		greeting.setGreeting(name+",吃饭了么");
		String payload = objectMapper.writer().writeValueAsString(greeting);
		source.output().send(MessageBuilder.withPayload(payload).build());
	}
}