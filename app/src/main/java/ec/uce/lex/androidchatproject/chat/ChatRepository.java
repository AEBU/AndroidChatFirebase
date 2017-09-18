package ec.uce.lex.androidchatproject.chat;

/**
 * Created by Alexis on 18/09/2017.
 */

public interface ChatRepository {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void destroyChatListener();
    void subscribeForChatUpates();
    void unSubscribeForChatUpates();

    void changeConnectionStatus(boolean online);

}
