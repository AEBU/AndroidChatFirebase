package ec.uce.lex.androidchatproject.addcontact.ui;

/**
 * Created by Alexis on 16/09/2017.
 */

public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();

}
