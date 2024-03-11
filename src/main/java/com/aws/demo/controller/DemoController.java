package com.aws.demo.controller;

import com.aws.demo.models.Employee;
import com.aws.demo.services.S3Service;
import com.aws.demo.services.SecretManagerService;
import com.aws.demo.services.SnsNotificationService;
import com.aws.demo.services.SqsNotification;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("demo")
@Tag(name = "Deepthi Demo App", description = "Demo Restful APIs")
public class DemoController {

    @Autowired
    private SnsNotificationService snsService;

    @Autowired
    private SqsNotification sqsNotification;

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "sendNotification",consumes =  MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "upload",consumes =   MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestBody Employee employee)
    {
        String response = s3Service.uploadFile(employee);
        ResponseEntity<String> returnValue = new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        return  returnValue;
    }
    @GetMapping(value = "download/{eno}",consumes =  "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> downloadFile(@PathVariable("eno") Integer  employeeNumber)
    {
        Employee employee = s3Service.downFile(employeeNumber);
        ResponseEntity<Employee> returnValue = new ResponseEntity<>(employee, HttpStatus.ACCEPTED);
        return  returnValue;
    }
    @GetMapping(value = "get-credentials",consumes =  "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCredentilas()
    {
        SecretManagerService secretManagerService = SecretManagerService.getInstance();
        String credentials = String.format("username=%s;password=%s",secretManagerService.getUserName(),secretManagerService.getPassword());
        ResponseEntity<String> returnValue = new ResponseEntity<>(credentials, HttpStatus.ACCEPTED);
        return  returnValue;
    }
}
