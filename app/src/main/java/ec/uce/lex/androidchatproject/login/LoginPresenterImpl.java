package ec.uce.lex.androidchatproject.login;

/**
 * Login, esta clase tiene una vista LoginVIew
 * y va a tener un interactuador LoginInteractor
 *
 * Login View como parámetro en el constuctor
 * Created by Alexis on 29/08/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {

    LoginView loginView;
    LoginInteractor loginInteractor;


    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onCreate() {

    }

    /*
    * Hace null a la vista, para evitar el MEMORY LICK
    * */
    @Override
    public void onDestroy() {
        loginView=null;
    }

    /*
    * Revisamos si esta autenticado o no, requiere de la vista
    * por lo que primero revisamos si la vista existe y si acaso lo es
    * llamamos a los metodos que desabilitan los inpus y mostrar el progressbar
    * En pocas revisamos si existe una sesión de un usuairio autenticado
    *
    * Luego llamamos al interactuador y lo usamos con check Session
    * */
    @Override
    public void checkForAuthenticatedUser() {
        if  (loginView !=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.checkAlreadyAuthenticated();
    }


    /*
    * Validacion del login lo smismo que si esta autenticado
    * pero en el login interactor necesito el metodo doSignIN que
    * me ingresa a la lógica de Firebase para hacer login
    * */
    @Override
    public void validateLogin(String email, String password) {
        if (loginView !=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email,password);
    }

    /*
    * Registrar nuevo usuario,
    * pero en el login interactor necesito el metodo doSignIN que
    * me ingresa a la lógica de Firebase para crear un usuario
    * */
    @Override
    public void registerNewUser(String email, String password) {
        if (loginView !=null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email,password);
    }

    /*
    * TEngo un flujo de llamadas en una via, presentador recibe informacion del
    * interactuador , por lo que es necesario reportar a la vista cuando se tuvo exito o no
    * para cuando usemos el repository, podemos usar con listener o eventBus
    * en todos la visat diferente de null porque estamos usando métodos de vista
    * */


    /*
    *Cuando tengamos exito iniciando la sesiñon solo vamos a navigateToMainScreen
    * */
    private void onSignInSuccess(){
        if (loginView !=null){
            loginView.navigateToMainScreen();
        }
    }


    /*
    * Cuando creamos un nuevo usuario le avisamos a la vista
    * que este usuario fue cerado correctamente
    * */
    private void onSignUpSuccess(){
        if (loginView !=null){
            loginView.newUserSuccess();
        }
    }

    /*
    *REactivamos los inputs y esconder el progressBar
    * para que el usuario corrija los inputs y reportar que hubo
    * el error
    * */
    private void onSignInError(String error){
        if (loginView !=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    /*
    *REactivamos los inputs y esconder el progressBar
    * para que el usuario corrija los inputs y reportar que hubo
    * el error
    * */
    private void onSignUpError(String error){
        if (loginView !=null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }
}
