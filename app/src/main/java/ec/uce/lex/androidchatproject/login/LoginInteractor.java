package ec.uce.lex.androidchatproject.login;

/**
 * Para los casos de uso, los trabaja a los casos de uso
 * para si en caso existe la sesión
 * para ver si inicio de sesión o crearme la cuenta
 * Created by Alexis on 29/08/2017.
 */

public interface LoginInteractor {
    void checkAlreadyAuthenticated();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);

}
