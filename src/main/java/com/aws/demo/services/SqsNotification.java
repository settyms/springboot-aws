package com.aws.demo.services;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsNotification {

    @Autowired
    private SqsTemplate sqsTemplate;

    @Value("${aws.sqs-send-queue-name}")
    private String queueName;

    public String sendMessage(String message)
    {
        SendResult result =sqsTemplate.send(queueName,message);
        return "Msg is sent to SQS queue";
    }
}
