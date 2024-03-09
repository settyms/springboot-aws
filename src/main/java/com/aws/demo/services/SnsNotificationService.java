package com.aws.demo.services;

import com.aws.demo.models.Employee;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SnsNotificationService {
    private final SnsTemplate snsTemplate;
    @Value("${aws.sns-topic-name}")
    private String snsTopicName;

    public  SnsNotificationService(SnsTemplate template)
    {
        this.snsTemplate = template;
    }
    public String sendNotification(Employee employee)
    {
        this.snsTemplate.sendNotification(snsTopicName,employee,"From Restful Service");
        return "Message is sent";
    }


}
