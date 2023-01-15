package com.example.uberapp.core.dto;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllMessagesDTO implements Serializable {
    private long totalCount;
    private List<MessageDTO> results;

    public AllMessagesDTO(long totalCount, List<MessageDTO> results){
        this.totalCount = totalCount;
        this.results = results;
    }

    public AllMessagesDTO(Stream<MessageDTO> results){
        this.results = results.collect(Collectors.toList());
        this.totalCount = this.results.size();
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<MessageDTO> getMessages() {
        return results;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.results = messages;
    }
}
