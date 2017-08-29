package ec.uce.lex.androidchatproject.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ec.uce.lex.androidchatproject.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editTxtEmail)       EditText inputEmail;
    @BindView(R.id.btnSignin)          Button btnSignin;
    @BindView(R.id.btn_Signup)         Button btnSignup;
    @BindView(R.id.editTxtPassword)    EditText inputPassword;
    @BindView(R.id.progressBar)        ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSignin, R.id.btn_Signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignin:
                Log.e("Signin",inputEmail.getText().toString());
                break;
            case R.id.btn_Signup:
                Log.e("Signup",inputPassword.getText().toString());
                break;
        }
    }
}
