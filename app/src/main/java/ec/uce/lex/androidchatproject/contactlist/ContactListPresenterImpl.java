package ec.uce.lex.androidchatproject.contactlist;

import android.util.EventLogTags;

import ec.uce.lex.androidchatproject.contactlist.events.ContactListEvent;
import ec.uce.lex.androidchatproject.contactlist.ui.ContactListView;
import ec.uce.lex.androidchatproject.entities.User;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;

/**
 * Created by Alexis on 08/09/2017.
 */

public class ContactListPresenterImpl implements ContactListPresenter{
    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor listInteractor;
    private ContactListSessionInteractor sessionInteractor;

    public ContactListPresenterImpl(ContactListView view){
        eventBus = GreenRobotEventBus.getInstance();
        this.view = view;
        this.listInteractor = new ContactListInteractorImpl();
        this.sessionInteractor = new ContactListSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        sessionInteractor.changeConnectionStatus(User.ONLINE);
        listInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);

    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        listInteractor.destroyListener();
        view = null;
    }

    @Override
    public void signOff() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
        listInteractor.destroyListener();
        sessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Override
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getEventType()){
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }
    private void onContactAdded(User user){
        if (view != null){
            view.onContactAdded(user);
        }
    }

    private void onContactChanged(User user){
        if (view != null){
            view.onContactChanged(user);
        }
    }

    private void onContactRemoved(User user){
        if (view != null){
            view.onContactRemoved(user);
        }
    }
}
