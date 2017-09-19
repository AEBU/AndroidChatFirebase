package ec.uce.lex.androidchatproject.chat;

import org.greenrobot.eventbus.Subscribe;

import ec.uce.lex.androidchatproject.chat.events.ChatEvent;
import ec.uce.lex.androidchatproject.chat.ui.ChatView;
import ec.uce.lex.androidchatproject.entities.ChatMessage;
import ec.uce.lex.androidchatproject.entities.User;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;

/**
 * Created by Alexis on 18/09/2017.
 */
public class ChatPresenterImpl implements ChatPresenter {

    EventBus eventBus;
    ChatView view;
    ChatInteractor chatInteractor;
    ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView chatView){
        this.view = chatView;
        this.eventBus = GreenRobotEventBus.getInstance();

        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();
    }


    @Override
    public void onPause() {
        chatInteractor.unSubscribeForChatUpates();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribeForChatUpates();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyChatListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        this.chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }


    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (view != null) {
            ChatMessage msg = event.getMessage();
            view.onMessageReceived(msg);
        }
    }
}
