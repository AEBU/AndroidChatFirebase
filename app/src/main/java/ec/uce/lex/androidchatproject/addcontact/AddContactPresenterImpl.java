package ec.uce.lex.androidchatproject.addcontact;

import org.greenrobot.eventbus.Subscribe;

import ec.uce.lex.androidchatproject.addcontact.events.AddContactEvent;
import ec.uce.lex.androidchatproject.addcontact.ui.AddContactView;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;

/**
 * Created by Alexis on 16/09/2017.
 */
public class AddContactPresenterImpl implements AddContactPresenter {

    private EventBus eventBus;
    private AddContactView view;
    private AddContactInteractor interactor;


    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if (view != null) {
            view.hideInput();
            view.showProgress();
        }

        interactor.addContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (view != null) {
            view.hideProgress();
            view.showInput();

            if (event.isError()) {
                view.contactNotAdded();
            } else {
                view.contactAdded();
            }
        }
    }
}
