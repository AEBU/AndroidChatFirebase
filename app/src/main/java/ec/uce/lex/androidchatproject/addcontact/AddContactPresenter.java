package ec.uce.lex.androidchatproject.addcontact;

import ec.uce.lex.androidchatproject.addcontact.events.AddContactEvent;

/**
 * Created by Alexis on 16/09/2017.
 */

public interface AddContactPresenter {

    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}

