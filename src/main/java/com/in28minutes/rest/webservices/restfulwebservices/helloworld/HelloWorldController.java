package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

///REST API
@RestController
public class HelloWorldController {


	@GetMapping(value = "/hello-world")
	public String helloWorld()
	{
		return "hello world";
	}

}
