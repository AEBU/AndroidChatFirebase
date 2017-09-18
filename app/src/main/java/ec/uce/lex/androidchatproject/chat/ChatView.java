package ec.uce.lex.androidchatproject.chat;

import ec.uce.lex.androidchatproject.entities.ChatMessage;

/**
 * Created by Alexis on 18/09/2017.
 */

public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
