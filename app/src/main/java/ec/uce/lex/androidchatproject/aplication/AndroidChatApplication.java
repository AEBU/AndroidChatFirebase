package ec.uce.lex.androidchatproject.aplication;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Alexis on 28/08/2017.
 */

public class AndroidChatApplication extends Application {
    private FirebaseAuth mAuth;


    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    /*
    * Enviaremos un contexto para que se cree cada vez que creamos nuestra
    * aplicacion de firebase soporte para caracteristicas OFFLINE
    *
    * */
    private void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
