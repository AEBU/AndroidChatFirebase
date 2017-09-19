package ec.uce.lex.androidchatproject.chat;

/**
 * Created by Alexis on 18/09/2017.
 */
public class ChatInteractorImpl implements ChatInteractor {

     private ChatRepository chatRepository;

    public ChatInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        chatRepository.sendMessage(msg);
    }

    @Override
    public void setRecipient(String recipient) {
        chatRepository.setRecipient(recipient);
    }

    @Override
    public void destroyChatListener() {
        chatRepository.destroyChatListener();
    }

    @Override
    public void subscribeForChatUpates() {
        chatRepository.subscribeForChatUpates();
    }

    @Override
    public void unSubscribeForChatUpates() {
        chatRepository.unSubscribeForChatUpates();
    }
}
