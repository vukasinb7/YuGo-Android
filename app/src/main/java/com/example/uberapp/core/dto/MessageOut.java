package org.yugo.backend.YuGo.dto;

import com.example.uberapp.core.model.Message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
@NoArgsConstructor
public class MessageOut {
    private Integer id;
    private LocalDateTime timeOfSending;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private String type;
    private Integer rideId;

    public MessageOut(Integer id, LocalDateTime timeOfSending, Integer senderId, Integer receiverId, String message, String type, Integer rideId) {
        this.id = id;
        this.timeOfSending = timeOfSending;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public MessageOut(Message message) {
        this(message.getId(), message.getTimeOfSending(), message.getSender().getId(), message.getReceiver().getId(),
                message.getMessageContent(), message.getMessageType().toString(), message.getRide().getId());
    }
}
