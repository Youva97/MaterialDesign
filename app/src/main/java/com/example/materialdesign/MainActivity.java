package com.example.materialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout email, login, password, passwordRepeat;
    private Button btnRegister;
    private ProgressBar pgBar;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^"+
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#!$%^&+=])" +
            "(?=\\S+$)" +
            ".{6,20}" +
            "$");

    private String strLogin, strEmail, strPassword, strPasswordRepeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        passwordRepeat = findViewById(R.id.password_repeat);
        btnRegister = findViewById(R.id.btnRegister);
        pgBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validLogin();
                validEmail();
                validPassword();
                validPasswordRepeat();

                if (!validEmail() || !validLogin() || !validPassword() || !validPasswordRepeat())
                    return;

                pgBar.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.GONE);
            }
        });
    }

    private boolean validLogin() {
        strLogin = login.getEditText().getText().toString().trim().toLowerCase();
        if(strLogin.isEmpty()) {
            login.setError("Le login doit être renseigné");
            login.requestFocus();
            return false;
        } else if (strLogin.length() > 10) {
            login.setError("Le login est trop long");
            return false;
        } else {
            login.setError(null);
            return true;
        }
    }

    private boolean validEmail() {
        strEmail = email.getEditText().getText().toString().trim().toLowerCase();
        if(strEmail.isEmpty()) {
            email.setError("L'email doit être renseigné");
            email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError(strEmail + "n'est pas un email valide");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validPassword() {
        strPassword = password.getEditText().getText().toString().trim().toLowerCase();
        strPasswordRepeat = passwordRepeat.getEditText().getText().toString().trim().toLowerCase();
        if(strPassword.isEmpty()) {
            password.setError("Le mot de passe doit être renseigné");
            password.requestFocus();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(strPassword).matches()) {
            password.setError("Le mot de passe saisi ne respecte pas les règles de validation");
            return false;
        } else if (!strPassword.equals(strPasswordRepeat)) {
            password.setError("Les deux mots de passe saisis ne sont pas identiques");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean validPasswordRepeat() {
        if(strPasswordRepeat.isEmpty()) {
            passwordRepeat.setError("Le mot de passe doit être confirmé");
            passwordRepeat.requestFocus();
            return false;
        } else if (!strPassword.equals(strPasswordRepeat)) {
            passwordRepeat.setError("Les deux mots de passe saisis ne sont pas identiques");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(strPasswordRepeat).matches() ) {
            passwordRepeat.setError("Le mot de passe saisi ne respecte pas les règles de validation");
            return false;
        } else {
            passwordRepeat.setError(null);
            return true;
        }
    }
}