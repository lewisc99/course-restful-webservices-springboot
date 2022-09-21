package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

///REST API
@RestController
public class HelloWorldController {


	private MessageSource messageSource;

	public HelloWorldController(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}


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

	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized()
	{

		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message",null,"Default Message",locale);

	}
}
