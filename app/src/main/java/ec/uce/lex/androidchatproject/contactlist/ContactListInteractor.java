package ec.uce.lex.androidchatproject.contactlist;

/**
 * Created by Alexis on 08/09/2017.
 */

public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);

}
