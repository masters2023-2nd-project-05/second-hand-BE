package com.team5.secondhand.chat.chatroom.domain;

import com.team5.secondhand.api.chatroom.dto.ChatroomInfo;
import com.team5.secondhand.api.chatroom.exception.NotChatroomMemberException;
import com.team5.secondhand.chat.bubble.domain.ChatBubble;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@RedisHash("chatroom")
public class Chatroom implements Serializable { // NoSQL 에 저장될 자료 구조
    @Id
    private String chatroomId;
    private Participants participants = new Participants(new ConcurrentHashMap<>());
    private String lastMessage;
    private Instant updateAt;

    @Builder
    private Chatroom(String chatroomId, Participants participants, String lastMessage, Instant updateAt) {
        this.chatroomId = chatroomId;
        this.participants = participants;
        this.lastMessage = lastMessage;
        this.updateAt = updateAt;
    }

    public static Chatroom init(ChatroomInfo info) {
        return Chatroom.builder()
                .chatroomId(info.getRoomId())
                .participants(Participants.init(info.getMembers()))
                .lastMessage("")
                .build();
    }

    public static Chatroom create(String chatroomId, String memberId) {
        return Chatroom.builder()
                .chatroomId(chatroomId)
                .participants(Participants.init(List.of(memberId)))
                .lastMessage("")
                .build();
    }

    public boolean updateLastMessage (ChatBubble chatBubble) throws NotChatroomMemberException {
        this.lastMessage = chatBubble.getMessage();
        this.updateAt = Instant.now();
        return participants.getMessage(chatBubble.getReceiver());
    }

    public boolean enter(String memberId) {
        return participants.enter(memberId);
    }

    public boolean exit(String memberId) {
        return participants.exit(memberId);
    }
}
