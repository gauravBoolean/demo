package com.example.ibanvalidator.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidatorController {

    @Autowired
    ValidatorService service;

    @GetMapping
    public ResponseEntity<?> validate(@RequestParam String ibanNumber){

        return ResponseEntity.ok(service.validateIBANNumber(ibanNumber));
    }
}
