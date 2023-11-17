package com.example.Kafka.controller;

import com.example.Kafka.ApplicationConstants.ApplicationConstants;
import com.example.Kafka.DTO.InformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping("/{message}")
    public String sendMessage(@PathVariable String message) {
        InformationDTO info = new InformationDTO();
        info.setMessage(message);
        kafkaTemplate.send(ApplicationConstants.KAFKA_TOPIC_ACCESS_NAME, message);
        return "Message sent successfully";
    }

    @KafkaListener(topics = ApplicationConstants.KAFKA_TOPIC_ACCESS_NAME, groupId = ApplicationConstants.KAFKA_CONSUMER_GROUP_ID)
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group " + ApplicationConstants.KAFKA_CONSUMER_GROUP_ID + " : " + message);
    }
}
