package ec.uce.lex.androidchatproject.contactlist;

import ec.uce.lex.androidchatproject.contactlist.events.ContactListEvent;

/**
 * Created by Alexis on 08/09/2017.
 */

public interface ContactListPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);


}
