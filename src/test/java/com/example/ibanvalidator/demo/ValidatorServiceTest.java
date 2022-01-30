package com.example.ibanvalidator.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorServiceTest {

    private ValidatorService service;

    @BeforeEach
    public void setup(){
        service = new ValidatorService();
    }

    @Test
    public void testFetchValidator(){
        IBANValidator validator = new IBANValidator("GB", 22, "GB\\d{2}[A-Z]{4}\\d{14}" , "United Kingdom");
        Assertions.assertEquals(validator, service.fetchValidator("GB82WEST12345698765432"));
    }

    @Test
    public void testFetchValidatorFailed(){
        Assertions.assertThrows(InvalidIBANCodeException.class, () -> service.fetchValidator("AA82WEST12345698765432"));
    }

    @Test
    public void testCheckMinLengthFailed(){
        Assertions.assertThrows(InvalidIBANCodeException.class, () -> service.checkMinLength("A"));
    }

    @Test
    public void testValidateIBANNumber(){
        Assertions.assertEquals("Valid IBAN number for United Kingdom", service.validateIBANNumber("GB82WEST12345698765432"));
    }

    @Test
    public void testValidateIBANNumberFailed(){
        Assertions.assertThrows(InvalidIBANCodeException.class, () -> service.validateIBANNumber("AA82WEST12345698765432"));
    }


}
