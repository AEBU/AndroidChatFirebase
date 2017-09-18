package ec.uce.lex.androidchatproject.addcontact;

/**
 * Created by Alexis on 18/09/2017.
 */
public class AddContactInteractorImpl implements AddContactInteractor {
    AddContactRepository repository;

    public AddContactInteractorImpl() {
        this.repository = new AddContactRepositoryImpl();
    }

    @Override
    public void addContact(String email) {
        repository.addContact(email);
    }
}
