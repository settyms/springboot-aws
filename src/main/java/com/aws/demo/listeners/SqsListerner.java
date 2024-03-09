package com.aws.demo.listeners;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqsListerner {

    @SqsListener(value = "${aws.sqs-queue-name}",acknowledgementMode ="MANUAL")
    public void processMessage(String message, Acknowledgement acknowledgement)
    {
        log.info("the message received is {}",message);
        acknowledgement.acknowledge();
    }
}
