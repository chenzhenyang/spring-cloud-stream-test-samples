package com.example.demo.app;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

	String INPUT = "myinput";

	@Input(MySink.INPUT)
	SubscribableChannel input();

}
