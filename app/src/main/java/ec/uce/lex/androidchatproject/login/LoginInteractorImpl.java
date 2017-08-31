package ec.uce.lex.androidchatproject.login;



/**
 * Represnta el caso de uso que tenemos y tenemos un login Repository
 * y sobre este hacemos las llamadas necesarias
 *  No tengo el Repositorio, por lo que voy unicamente llamando en cascada
 * Created by Alexis on 31/08/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;


    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkAlreadyAuthenticated() {
        loginRepository.checkAlreadyAuthenticated();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.signUp(email,password);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email,password);
    }
}
