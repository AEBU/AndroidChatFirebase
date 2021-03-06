package ec.uce.lex.androidchatproject.entities;

import com.google.firebase.database.Exclude;

/**
 * Created by Alexis on 30/12/2016.
 */

public class ChatMessage {

    private String msg;
    private String sender;
    @Exclude
    private boolean sentByMe;


    public ChatMessage() {
    }

    public ChatMessage(String sender, String msg) {
        this.sender = sender;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(Boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof ChatMessage){
            ChatMessage msg= (ChatMessage)obj;
            equal = this.sender.equals(msg.getSender())&& this.msg.equals(msg.getMsg())&&this.sentByMe == msg.sentByMe;

        }

        return equal;
    }
}
