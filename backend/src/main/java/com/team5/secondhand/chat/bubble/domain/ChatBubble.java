package com.team5.secondhand.chat.bubble.domain;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@NoArgsConstructor
public class ChatBubble implements Serializable {

    private Long id;
    private String chatroomId;
    private Long sender;
    private Long receiver;
    private String message;
    private String createdAt;

    @Builder
    private ChatBubble(Long id, String chatroomId, Long sender, Long receiver, String message, String createdAt) {
        this.id = id;
        this.chatroomId = chatroomId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.createdAt = createdAt;
    }

    private String generateCreatedAt(String time) {
        if (time == null) {
            return Instant.now().toString();
        }
        return time;
    }

    public Boolean isSender(String memberId) {
        return this.sender.equals(memberId);
    }

    public void ready() {
        this.createdAt = generateCreatedAt(createdAt);
    }
}
