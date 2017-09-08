package ec.uce.lex.androidchatproject.contactlist;

/**
 * Created by Alexis on 08/09/2017.
 */

public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void destroyListener();
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void changeConnectionStatus(boolean online);

}
