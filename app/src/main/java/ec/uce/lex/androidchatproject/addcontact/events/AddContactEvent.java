package ec.uce.lex.androidchatproject.addcontact.events;

/**
 * Created by Alexis on 16/09/2017.
 */
public class AddContactEvent {

    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
