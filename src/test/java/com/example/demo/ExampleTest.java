package com.example.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.app.Greeting;
import com.example.demo.app.GreetingProducer;
import com.example.demo.app.MyProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExampleTest {

	@Autowired
	private Sink sink;

	@Autowired
	private Source source;

	@Autowired
	private MessageCollector messageCollector;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private GreetingProducer greetingSender;

	@Autowired
	private MyProcessor myProcessor;

	@Test
	@SuppressWarnings("unchecked")
	public void testProduceMessage() throws IOException {
		greetingSender.sayHello("小明");
		Message<String> received = (Message<String>) messageCollector.forChannel(source.output()).poll();
		System.out.println(received.getPayload());
		assertThat(received.getPayload(), equalTo("hello world"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testConsumerMessage() throws JsonProcessingException {
		Greeting greeting = new Greeting();
		greeting.setGreeting("testConsumerMessage");
		Message<String> message = new GenericMessage<>(
				objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(greeting));
		sink.input().send(message);
		assertThat(message.getPayload(), equalTo("hello world"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testInOut() {
		Message<String> message = new GenericMessage<>("testInOut");
		myProcessor.input().send(message);
		Message<String> received = (Message<String>) messageCollector.forChannel(myProcessor.output()).poll();
		assertThat(received.getPayload(), equalTo("hello world"));
	}

}