package ec.uce.lex.androidchatproject.chat;

/**
 * Created by Alexis on 18/09/2017.
 */
public class ChatSessionInteractorImpl implements ChatSessionInteractor {
    private ChatRepository repository;

    public ChatSessionInteractorImpl() {
        this.repository=new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
