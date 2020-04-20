package com.aws.codepipeline.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(value = "/hello-world",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
public class HelloWorldController {

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok()
                .body("Hello World !!! - New version - Develop 3");
    }
}
