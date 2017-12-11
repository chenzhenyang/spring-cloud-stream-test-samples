package com.example.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Transformer;

@EnableBinding(MyProcessor.class)
public class TransformProcessor {
	
	@Autowired
	private MyProcessor channels;

	@Transformer(inputChannel = MyProcessor.INPUT, outputChannel = MyProcessor.OUTPUT)
	public String transform(String in) {
		return in + " world";
	}
}
