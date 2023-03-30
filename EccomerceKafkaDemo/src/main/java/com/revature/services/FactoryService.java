package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FactoryService {

    private static final Logger logger = LoggerFactory.getLogger(FactoryService.class);

    @KafkaListener(topics = "factory")
    public void consumeWorkOrder(String message){
        logger.info("Work order received: " + message);
    }
}

