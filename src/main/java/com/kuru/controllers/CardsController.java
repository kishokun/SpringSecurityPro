package com.kuru.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
	
	@GetMapping("/myCards")
	public String getCardDetails(String input) {
		return "Here are card details from the DB";
	}

}
