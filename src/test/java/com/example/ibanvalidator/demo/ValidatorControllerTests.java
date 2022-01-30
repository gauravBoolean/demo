package com.example.ibanvalidator.demo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidatorControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSuccessMessage() throws Exception {
        String strResponse = this.restTemplate.getForObject("http://localhost:" + port + "?ibanNumber=GB82WEST12345698765432",
                String.class);
        assertEquals( "Valid IBAN number for United Kingdom", strResponse);
    }

    @Test
    public void testInvalidCodeMessage() throws Exception {
        String strResponse = this.restTemplate.getForObject("http://localhost:" + port + "?ibanNumber=GB82WEST1234569876543",
                String.class);
        assertEquals( "Invalid IBAN Number", strResponse);
    }

    @Test
    public void testUnrecognisedCodeMessage() throws Exception {
        String strResponse = this.restTemplate.getForObject("http://localhost:" + port + "?ibanNumber=BB82WEST1234569876543",
                String.class);
        assertEquals( "Unrecognized IBAN Number", strResponse);
    }



}
