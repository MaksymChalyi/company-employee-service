package com.maksimkaxxl.springbootrestfulapi.dtos;

import lombok.Builder;

@Builder
public record KafkaMailSenderDto(
        String subject,
        String content,
        String recipientEmail) {
}
