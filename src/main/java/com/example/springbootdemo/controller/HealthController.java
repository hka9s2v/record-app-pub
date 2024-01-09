package com.example.springbootdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin
public class HealthController{
    /**
     * GET /health
     *
     * @return OK (status code 200)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/health"
    )
    public ResponseEntity<Void> healthGet() {
        return ResponseEntity.ok().build();
    }
}
