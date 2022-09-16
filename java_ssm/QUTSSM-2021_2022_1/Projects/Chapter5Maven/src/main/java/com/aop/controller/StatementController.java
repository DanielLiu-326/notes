package com.aop.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aop.service.TestService;

@Controller("statementController")
public class StatementController {
	@Autowired
	private TestService testService;

	public String test() {
		String message = "";
		testService.test();
		return message;
	}
}


