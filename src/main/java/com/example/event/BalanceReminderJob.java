package com.example.event;

import com.example.event.model.EventMessage;
import com.example.service.GroupService;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class BalanceReminderJob {
    private final GroupService groupService;
    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "*/30 * * * * *")
    void sendBalanceReminders(){
        groupService.getAllGroups().forEach(groupEntity -> {
                    var groupBalances = groupService.getGroupBalances(groupEntity.getId(),null);
                    var balances = groupBalances.getBalances();
            balances.forEach(shareDto -> {
                kafkaProducer.publishBalanceReminder(
                        EventMessage.of(
                                Map.of(
                                        "groupId", groupEntity.getId(),
                                        "userId", shareDto.getUserId(),
                                        "balance", shareDto.getShare()
                                )
                        )
                );
            });

                }
        );
    }

}
