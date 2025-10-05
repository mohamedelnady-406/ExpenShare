package com.example.event;
import com.example.event.model.EventMessage;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface KafkaProducer {

    @Topic("user.created")
    void publishUserCreated(EventMessage event);
    @Topic("group.created")
    void publishGroupCreated(EventMessage event);
    @Topic("expense.added")
    void publishExpenseAdded(EventMessage event);
    @Topic("settlement.confirmed")
    void publishSettlementConfirmed(EventMessage event);



}
