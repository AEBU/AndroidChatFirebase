package ec.uce.lex.androidchatproject.login;

/**
 * Métdoos a utilizar dentro de la vista
 *      Habilitar o deshablitaiar los inputs que tengo, caja de texto, botones etc
 *      Mostrar u ocultar el progressbar
 *
 *      Métodos que me ayuden en el inicio de sesión, y el registrarse (manejarlo)se cnectan con el presentador
 *
 *      Métosdos para navegar a la pantalla principal,c on iniciao de sesión exitoso, y si no un error
 *
 *      Mostrar un mensaje si se registró exitosamente,o si no un error
 *
 *      Created by Alexis on 29/08/2017.
 */

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
