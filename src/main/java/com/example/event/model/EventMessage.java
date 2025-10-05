package com.example.event.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
@Serdeable
@AllArgsConstructor
@NoArgsConstructor
public class EventMessage {
    private String eventId;
    private Instant occurredAt;
    private Map<String, Object> payload;

    public static EventMessage of(Map<String, Object> payload) {
        return EventMessage.builder()
                .eventId(UUID.randomUUID().toString())
                .occurredAt(Instant.now())
                .payload(payload)
                .build();
    }
}
