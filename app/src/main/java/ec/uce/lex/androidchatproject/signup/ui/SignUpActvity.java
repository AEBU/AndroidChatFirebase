package ec.uce.lex.androidchatproject.signup.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ec.uce.lex.androidchatproject.R;

public class SignUpActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_actvity);
        setTitle(R.string.signup_title);
    }
}
