package ec.uce.lex.androidchatproject.login;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import ec.uce.lex.androidchatproject.domain.FirebaseHelper;

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
        Log.e(TAG,"SingUP");
    }

    @Override
    public void signIn(String email, String password) {
        Log.e(TAG,"SingIn");
    }

    @Override
    public void checkAlreadyAuthenticated() {
        Log.e(TAG,"Check Session");
    }
}
