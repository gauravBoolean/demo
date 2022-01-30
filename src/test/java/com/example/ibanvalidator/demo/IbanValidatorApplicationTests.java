package com.example.ibanvalidator.demo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IbanValidatorApplicationTests {

	@Autowired
	private ValidatorController validatorController;

	@Autowired
	private ValidatorService validatorService;

	@Test
	void contextLoads() {
		assertNotNull(validatorController);
		assertNotNull(validatorService);
	}

}
