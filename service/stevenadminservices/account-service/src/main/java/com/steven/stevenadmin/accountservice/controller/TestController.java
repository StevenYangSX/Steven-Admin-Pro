package com.steven.stevenadmin.accountservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test() {
        return "Auth Test";
    }
}
