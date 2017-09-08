package ec.uce.lex.androidchatproject.contactlist;

/**
 * Created by Alexis on 08/09/2017.
 */

public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);

}
