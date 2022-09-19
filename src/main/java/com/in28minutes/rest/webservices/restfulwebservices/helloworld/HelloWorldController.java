package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

///REST API
@RestController
public class HelloWorldController {


	@GetMapping(value = "/hello-world")
	public String helloWorld()
	{
		return "hello world";
	}


	@GetMapping(value = "/hello-world-bean")
	public HelloWorldBean helloWorldBean()
	{
		return new HelloWorldBean("hello world");
	}


	//Path Parameters
	// /users/{id}/todos/{id} => /users/2/todos/200
	@GetMapping(path = "/hello/world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name)
	{
		return new HelloWorldBean(
				String.format("Hello world, %s",name)
		);
	}
}
