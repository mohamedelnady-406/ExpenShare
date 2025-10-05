package com.example.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

import jakarta.inject.Singleton;

@Singleton
@KafkaListener
public class KafkaConsumers {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumers.class);

    @Topic("user.created")
    public void consumeUserCreation(String msg) {
        log.info("Received user created: {}", msg);
    }
    @Topic("group.created")
    public void consumeGroupCreation(String msg){
        log.info("Received group created: {}", msg);
    }
    @Topic("expense.added")
    public void consumeExpenseAddition(String msg){
        log.info("An Expense have been added: {}", msg);
    }
    @Topic("settlement.confirmed")
    public void consumeConfirmedSettlement(String msg){
        log.info("A settlement have been confirmed: {}", msg);
    }
}


