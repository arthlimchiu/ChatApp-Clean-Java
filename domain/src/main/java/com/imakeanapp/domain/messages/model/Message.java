package com.imakeanapp.domain.messages.model;

public class Message {

    private String message;

    private String sender;

    private long sent;

    public Message() {
    }

    public Message(String message, String sender, long sent) {
        this.message = message;
        this.sender = sender;
        this.sent = sent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getSent() {
        return sent;
    }

    public void setSent(long sent) {
        this.sent = sent;
    }
}
