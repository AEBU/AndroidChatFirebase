package ec.uce.lex.androidchatproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Clase que encapsula la lógica de nuestra aplicación, debemos tomar en cuenta que
 * usamos solo la parte de SingleTon para una instancia dentro de toda la aplicación
 * Created by Alexis on 28/08/2017.
 */

public class FirebaseHelper  {

    private DatabaseReference dataReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";

    /*
    * Clase que va a tener una instancia de mi objeto definido aquí, estamos simulando
    * una sola manera de una única instancia del objeto
    * */
    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /*
    *   Este constructor va a inicializar el objeto de Firebase con su instancia
    * */
    public FirebaseHelper() {
        this.dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    /*
    * Retorna el email del usaurio autenticado
    * */
    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }
}
