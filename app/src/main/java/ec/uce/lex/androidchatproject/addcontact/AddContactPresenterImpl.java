package ec.uce.lex.androidchatproject.addcontact;

import ec.uce.lex.androidchatproject.addcontact.events.AddContactEvent;
import ec.uce.lex.androidchatproject.addcontact.ui.AddContactView;

/**
 * Created by Alexis on 16/09/2017.
 */
public class AddContactPresenterImpl implements AddContactPresenter {

    private AddContactView view;

    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void addContact(String email) {

    }

    @Override
    public void onEventMainThread(AddContactEvent event) {

    }
}
