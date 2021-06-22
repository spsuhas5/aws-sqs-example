package com.aws.sqs.awssqsexample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AwsSQSController {

    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public AwsSQSController(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @Value("${cloud.aws.end-point.uri}")
    private String endPoint;

    @GetMapping("/send/{message}")
    public void sendMessageToSQSQueue(@PathVariable String message) {
        queueMessagingTemplate.send(endPoint,
                MessageBuilder.withPayload(message).build());
    }

    @SqsListener("myFirstSQSQueue")
    public void loadMessageFromSQSQueue(String message) {
        log.info("message from SQS Queue {}", message);

    }
}
