package com.example.uberapp.core.tools;

import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MessageMockup {
    static int messageIdCounter = 5;
    static User pass1 = UserMockup.user01;
    static User pass2 = UserMockup.user02;
    static String sample_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque sodales sem sit amet ipsum scelerisque consectetur. Vestibulum pellentesque diam sit amet diam commodo laoreet. Vivamus lobortis aliquam neque id hendrerit.";
    static Message msg1 = new Message("001", "4"+sample_text,
            LocalDateTime.of(2022,11,13,17,10),
            pass1, pass2, MessageType.Drive, null);

    static Message msg2 = new Message("002", "1"+sample_text,
            LocalDateTime.of(2021,12,12,17,13),
            pass1, pass2, MessageType.Drive, null);

    static Message msg3 = new Message("003", sample_text,
            LocalDateTime.of(2022,11,12,17,50),
            pass2, pass1, MessageType.Drive, null);

    static Message msg4 = new Message("004", "3"+sample_text,
            LocalDateTime.of(2021,12,12,17,55),
            pass1, pass2, MessageType.Drive, null);

    static Message msg5 = new Message("005", "2"+sample_text,
            LocalDateTime.of(2021,12,12,17,20),
            pass1, pass2, MessageType.Drive, null);

    static List<Message> messages = new ArrayList<>(Arrays.asList(msg1,msg2,msg3,msg4,msg5));

    public static List<Message> getConversationMessages(){
        messages.sort(Comparator.comparing(Message::getSendDateTime));
        return messages;
    }

    public static List<Message> getConversations(){
        HashMap<String, Message> hashMap = new HashMap<>();
        messages.sort(Comparator.comparing(Message::getSendDateTime));
        for(int i=0 ; i < messages.size(); i++){
            if (messages.get(i).getSender().getId().equals(UserMockup.getLoggedUser().getId())){
                Message tempMess = new Message(messages.get(i));
                tempMess.setText("Me: " + tempMess.getText());
                User tempReceiver = tempMess.getReceiver();
                User tempSender = tempMess.getSender();
                tempMess.setSender(tempReceiver);
                tempMess.setReceiver(tempSender);
                hashMap.put(tempReceiver.getId(),tempMess);
            }
            else{
                hashMap.put(messages.get(i).getSender().getId(),messages.get(i));
            }
        }
        return new ArrayList<>(hashMap.values());
    }

    public static int getIdCounter(){
        return messageIdCounter;
    }

    public static void IncreaseIdCounter(){
        messageIdCounter++;
    }

    public static void addMessage(Message msg){
        messages.add(msg);
    }
}
