package ec.uce.lex.androidchatproject.login;

/**
 * Creado para la interacción con el backend Firebase
 * es la única clase que esta enterada que estamos usando
 * Firebase
 * Created by Alexis on 29/08/2017.
 */

public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkAlreadyAuthenticated();
}
