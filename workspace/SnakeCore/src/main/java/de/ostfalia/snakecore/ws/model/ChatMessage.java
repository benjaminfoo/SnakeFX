package de.ostfalia.snakecore.ws.model;

import java.util.Date;

public class ChatMessage {

    private String from;
    private String text;
    private Date date;

    public ChatMessage() {
    }

    public ChatMessage(String from, String text) {
        this.from = from;
        this.text = text;
        this.date = new Date(System.currentTimeMillis());
    }

    public ChatMessage(String from, String text, Date date) {
        this.from = from;
        this.text = text;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}