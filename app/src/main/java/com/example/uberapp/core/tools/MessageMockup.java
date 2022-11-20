package com.example.uberapp.core.tools;

import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MessageMockup {

    public static List<Message> getConversation(){
        List<Message> messages = new ArrayList<>();

        User pass1 = UserMockup.getUsers().get(0);
        User pass2 = UserMockup.getUsers().get(1);

        String sample_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque sodales sem sit amet ipsum scelerisque consectetur. Vestibulum pellentesque diam sit amet diam commodo laoreet. Vivamus lobortis aliquam neque id hendrerit.";
        Message mess1 = new Message("001", "4"+sample_text,
                LocalDateTime.of(2022,11,13,17,10),
                pass1, pass2, MessageType.Drive, null);

        Message mess2 = new Message("002", "1"+sample_text,
                LocalDateTime.of(2021,12,12,17,13),
                pass1, pass2, MessageType.Drive, null);

        Message mess3 = new Message("003", sample_text,
                LocalDateTime.of(2023,12,12,17,15),
                pass2, pass1, MessageType.Drive, null);

        Message mess4 = new Message("004", "3"+sample_text,
                LocalDateTime.of(2021,12,12,17,55),
                pass1, pass2, MessageType.Drive, null);

        Message mess5 = new Message("005", "2"+sample_text,
                LocalDateTime.of(2021,12,12,17,20),
                pass1, pass2, MessageType.Drive, null);

        messages.add(mess1);
        messages.add(mess2);
        messages.add(mess3);
        messages.add(mess4);
        messages.add(mess5);

        messages.sort(Comparator.comparing(Message::getSendDateTime));

        return messages;
    }

    public static List<Message> getDistinctMessages(){
        List<Message> messages = new ArrayList<>();

        User pass1 = UserMockup.getUsers().get(0);
        User pass2 = UserMockup.getUsers().get(1);

        String sample_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque sodales sem sit amet ipsum scelerisque consectetur. Vestibulum pellentesque diam sit amet diam commodo laoreet. Vivamus lobortis aliquam neque id hendrerit.";


        Message mess1 = new Message("001", "4"+sample_text,
                LocalDateTime.of(2022,11,13,17,10),
                pass1, pass2, MessageType.Drive, null);

        Message mess2 = new Message("002", "1"+sample_text,
                LocalDateTime.of(2021,12,12,17,13),
                pass1, pass2, MessageType.Drive, null);

        Message mess3 = new Message("003", sample_text,
                LocalDateTime.of(2023,12,12,17,15),
                pass2, pass1, MessageType.Drive, null);

        Message mess4 = new Message("004", "3"+sample_text,
                LocalDateTime.of(2021,12,12,17,55),
                pass1, pass2, MessageType.Drive, null);

        Message mess5 = new Message("005", "2"+sample_text,
                LocalDateTime.of(2021,12,12,17,20),
                pass1, pass2, MessageType.Drive, null);

        messages.add(mess1);
        messages.add(mess2);
        messages.add(mess3);
        messages.add(mess4);
        messages.add(mess5);

        HashMap<String, Message> hashMap = new HashMap<>();
        messages.sort(Comparator.comparing(Message::getSendDateTime));
        for(int i=0 ; i < messages.size(); i++){
            if (messages.get(i).getSender().getId().equals("001")){
                Message tempMess = messages.get(i);
                tempMess.setText("Me: " + tempMess.getText());
                User tempReciever = tempMess.getReceiver();
                User tempSender = tempMess.getSender();
                tempMess.setSender(tempReciever);
                tempMess.setReceiver(tempSender);
                hashMap.put(tempReciever.getId(),tempMess);
            }
            else{
                hashMap.put(messages.get(i).getSender().getId(),messages.get(i));
            }
        }
        return new ArrayList<>(hashMap.values());
    }
}
