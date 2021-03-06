package ec.uce.lex.androidchatproject.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ec.uce.lex.androidchatproject.R;
import ec.uce.lex.androidchatproject.contactlist.ui.ContactListActivity;
import ec.uce.lex.androidchatproject.login.LoginPresenter;
import ec.uce.lex.androidchatproject.login.LoginPresenterImpl;
import ec.uce.lex.androidchatproject.signup.SignUpView;
import ec.uce.lex.androidchatproject.signup.ui.SignUpActvity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.editTxtEmail)
    EditText inputEmail;
    @BindView(R.id.btnSignin)
    Button btnSignIn;
    @BindView(R.id.btn_Signup)
    Button btnSignUp;
    @BindView(R.id.editTxtPassword)
    EditText inputPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.activity_main)
    RelativeLayout container;


    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //inicializo al presentador, con la vista como argumento
        loginPresenter = new LoginPresenterImpl(this);
        //hago una llamada a oncrate desde el presentador
        loginPresenter.onCreate();
        //lo que hace es revisar al inciio si a tiene un usuario autenticado, es decir el progress bar
        loginPresenter.checkForAuthenticatedUser();
    }

    @Override
    protected void onDestroy() {
        //para evitar el memory lick
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    @OnClick(R.id.btn_Signup)
    public void handleSignUp() {
        startActivity(new Intent(this, SignUpActvity.class));

    }


    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        Intent i=new Intent(this, ContactListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        throw new UnsupportedOperationException("Operation is not valid in LoginActivity");
    }

    @Override
    public void newUserError(String error) {
        throw new UnsupportedOperationException("Operation is not valid in LoginActivity");
    }


    /*
    * @parameter enabled para ver si esta habilitado o no
    * */
    private void setInputs(boolean enabled) {
        btnSignIn.setEnabled(enabled);
        btnSignUp.setEnabled(enabled);
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
    }
}
