package ec.uce.lex.androidchatproject.contactlist;

/**
 * Created by Alexis on 15/09/2017.
 */
public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {
    ContactListRepository repository;

    public ContactListSessionInteractorImpl() {
        repository = new ContactListRepositoryImpl();

    }

    @Override
    public void signOff() {
        repository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {

        return repository.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
