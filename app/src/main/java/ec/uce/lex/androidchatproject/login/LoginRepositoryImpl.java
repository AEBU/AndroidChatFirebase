package ec.uce.lex.androidchatproject.login;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import ec.uce.lex.androidchatproject.domain.FirebaseHelper;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;
import ec.uce.lex.androidchatproject.login.event.LoginEvent;

/**
 *  el repositorio es el que va a estar enterado que estamos trabajando esto con FIrebase
 *  COn un FIrebaseHelper de dominio
 *  Con un constructor vamos a obtener una instancia , porque estamos usando SIngleton
 *
 * Created by Alexis on 31/08/2017.
 */

public class LoginRepositoryImpl implements LoginRepository {

    private static final String TAG="LoginRepositoryImpl";
    private FirebaseHelper helper;
    private DatabaseReference dataReference;
    private DatabaseReference myUserReference;

    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void signUp(String email, String password) {
        postEvent(LoginEvent.onSignUpSuccess);
    }

    @Override
    public void signIn(String email, String password) {
        postEvent(LoginEvent.onSignInSuccess);
    }

    @Override
    public void checkAlreadyAuthenticated() {
        postEvent(LoginEvent.onFailedToRecoverSession);
    }
    private void postEvent(int type) {
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

}
