package ec.uce.lex.androidchatproject.login;

import ec.uce.lex.androidchatproject.login.event.LoginEvent;

/**
 * Esta presentador, está vincualdo a la vista para poder acceder a todos los métodos(VIsta)
 * entonces por lo mismo como la vista va a ser una actividad es posible que tengamos una
 * MEMORY LICK, por lo que para evitarlo a la horar de destruir la vista, voy a destruir la variable
 * que asigne al presentador Ondestroy
 *
 * Tengo 3 opciones
 *      Revisar si el usuario ya fue autenticado , si existe una sesión abierta
 *      Revisar si es un inicio de sesión válido en base a email y password
 *      Registrar un nuevo usuario de la misma forma con email y password
 *
 *
 * Created by Alexis on 29/08/2017.
 */

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);

}
