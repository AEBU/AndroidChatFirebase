package ec.uce.lex.androidchatproject.chat;

import ec.uce.lex.androidchatproject.chat.events.ChatEvent;

/**
 * Created by Alexis on 18/09/2017.
 */

public interface ChatPresenter {

    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);

    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);

}
