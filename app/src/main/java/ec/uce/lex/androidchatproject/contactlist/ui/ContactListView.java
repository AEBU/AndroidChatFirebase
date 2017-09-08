package ec.uce.lex.androidchatproject.contactlist.ui;

import ec.uce.lex.androidchatproject.entities.User;

/**
 * Created by Alexis on 08/09/2017.
 */

public interface ContactListView {
    void onContactAdded(User user);
    void  onContactChanged(User user);
    void onContactRemoved(User user);
}
