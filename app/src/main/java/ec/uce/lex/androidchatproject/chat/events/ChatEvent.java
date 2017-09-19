package ec.uce.lex.androidchatproject.chat.events;

import ec.uce.lex.androidchatproject.entities.ChatMessage;

/**
 * Created by Alexis on 18/09/2017.
 */
public class ChatEvent {
    ChatMessage msg;

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMessage() {
        return msg;
    }
}
