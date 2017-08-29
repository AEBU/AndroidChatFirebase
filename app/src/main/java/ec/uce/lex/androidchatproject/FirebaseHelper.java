package ec.uce.lex.androidchatproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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

    /*
    * userReference hace referencia a dataReference que es la raiz y apunta a getRoot si lo cmabiamos
    * child la variable path . child el email
    * Cambiamos reemplazar el punto por el underscorp por que no funciona el punto
    * @parameter email recibe el mail
    * */

    public DatabaseReference getUserReference(String email){
        DatabaseReference userReference = null;
        if (email != null) {
            String emailKey = email.replace(".", "_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }
    /*
    * Faclita que al llamar a getUserReference lo haga directamente para mi usuario
    * y este encapuslad la lógica en mi clase
    * */
    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    /*
    * Me da los contactos mediante la referencia antes dirigida en userReferences
    * de algún mail o contacto
    * */
    public DatabaseReference getContactsReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }

    /*
    * Me da los contactos mediante la referencia de el usuario autenticado
    * */
    public DatabaseReference getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }
    /*
    * La referencia de un contacto a partir del correo del usuario y del mail del contacto
    * Por esto tengo primero la referencia del correo principal y luego
    * tengo un child de los contactos y del correo secundario
    * */
    public DatabaseReference getOneContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    /*
    *   Referncia para los chats, necesitamos un separador y necesito comparar para ternerlo por
    *   orden alfabético, tomando el hijo de la referencia que yo he definido
    * */
    public DatabaseReference getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if (keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    /*
    * Cambiará el status del usuario si esta conectado o no
    * Tenga una referencia a mi usuario, es decir que sea contacto, y que sea diferente de null
    * creo el hashmap y busco la referencia a mi usuario y le actualizo con el objeto hashmap,
    * tomamos en cuenta que tengo el key "online" y este es de mi POJO
    * y luego les notifico a mis contactos
    * */
    public void changeUserConnectionStatus(boolean online) {
        if (getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);

            notifyContactsOfConnectionChange(online);
        }
    }

    /*
    * Método que ayuda a notificar a cambiar el status,
    * pero llama a el método que cierra sesion, en este caso mandamos false porque
    * no está cerrando sesión
    * */
    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }


    /*
    *Este método si cierra sesión y llama ofline y true para que cierre sesión
    * */
    public void signOff(){
        //notifyContactsOfConnectionChange(User.OFFLINE, true);
        notifyContactsOfConnectionChange(false, true);
    }

    /*
    *   Obtengo mi correo a través de una llamada UserEmail
    *   y obtengo la referencia de los contactos que tengo, con un listener para un solo evento
    *   Hago un ciclo que reccore todos los contactos y por cada contacto verifico cual es el email
    *   respectivo desde mi email y el que manda de acuerdo al método
    *   para poder avisarle a mis contactos que estoy offline u online
    *   si estoy cerrando sesion entonces datarefence unauth
    *
    * */
    public void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                }
                if (signoff){
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
    }

}
