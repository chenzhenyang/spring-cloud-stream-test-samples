package com.example.demo.app;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class GreetingConsumer {

	@StreamListener(Sink.INPUT)
	public void receive(Greeting greeting) {
		System.out.println(greeting.getGreeting());
	}

}
