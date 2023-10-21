package com.team5.secondhand.chat.chatroom.repository.entity;

import com.team5.secondhand.chat.chatroom.domain.ParticipantInfo;
import com.team5.secondhand.chat.chatroom.domain.Participants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantInfoEntity {

    private String chatroomId;
    private Long memberId;
    private Boolean isConnected;
    private Integer messageStock;

    protected ParticipantInfoEntity(String chatroomId, Long memberId, Boolean isConnected, Integer messageStock) {
        this.chatroomId = chatroomId;
        this.memberId = memberId;
        this.isConnected = isConnected;
        this.messageStock = messageStock;
    }

    public static Map<Long, ParticipantInfoEntity> fromDomain(Participants participants, String chatroomId) {
        return participants.getInfo().entrySet().stream()
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, e -> new ParticipantInfoEntity(
                        chatroomId,
                        e.getKey(),
                        e.getValue().getIsConnected(),
                        e.getValue().getMessageStock()
                )));
    }

    public static Participants toDomain(Map<Long, ParticipantInfoEntity> participants) {

        ConcurrentMap<Long, ParticipantInfo> collect = participants.entrySet().stream()
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey,
                        e -> ParticipantInfo.of(
                                e.getValue().getMemberId(),
                                e.getValue().getIsConnected(),
                                e.getValue().getMessageStock()
                        )));

        return new Participants(collect);
    }
}
