package com.ashok.Kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@GetMapping("/api/v1/receive")
public class ReceiveDataController {

    private KafkaProducer kafkaProducer;

    public ReceiveDataController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message received");
    }
}
