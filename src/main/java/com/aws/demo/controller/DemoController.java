package com.aws.demo.controller;

import com.aws.demo.models.Employee;
import com.aws.demo.services.SnsNotificationService;
import com.aws.demo.services.SqsNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private SnsNotificationService snsService;

    @Autowired
    private SqsNotification sqsNotification;

    @PostMapping(value = "sendNotification",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendSnsMessage(@RequestBody Employee employee)
    {
        String response = snsService.sendNotification(employee);
        ResponseEntity<String> returnValue = new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        return  returnValue;
    }

    @PostMapping(value = "sendSqsMessage",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendSqsMessage(@RequestBody String message)
    {
        String response = sqsNotification.sendMessage(message);
        ResponseEntity<String> returnValue = new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        return  returnValue;
    }
}
