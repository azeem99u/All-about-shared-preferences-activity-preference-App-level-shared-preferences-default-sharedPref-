package com.example.android.sharedpreferenceswithlogin;

import static com.example.android.sharedpreferenceswithlogin.App.LOGIN_STATUS_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    public static final String PREF_EMAIL_KEY = "PREF_EMAIL_KEY_my";
    public static final String USER_INFO_DATA_KEY = "USER_INFO_DATA_KEY";
    private EditText mUserEmail, mUserPassword;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        boolean isLogin = App.mySharedPreferences.getBoolean(LOGIN_STATUS_KEY, false);
//        if (isLogin) {
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//            finish();
//        }
        SharedPreferences sharedPreferences = App.mySharedPreferences;
        mUserEmail = findViewById(R.id.userEmail);
        mUserPassword = findViewById(R.id.userPassword);
        login = findViewById(R.id.login);


        String email = sharedPreferences.getString(PREF_EMAIL_KEY, "");
        mUserEmail.setText(email);

        login.setOnClickListener(view -> {
            attemptLogin();
        });
    }

    private void attemptLogin() {
        mUserEmail.setError(null);
        mUserPassword.setError(null);
        String email = mUserEmail.getText().toString().replace(" ", "");
        String password = mUserPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mUserEmail.setError(getString(R.string.error_field_required));
            focusView = mUserEmail;
            cancel = true;
        } else if (!isValidEmail(email)) {
            mUserEmail.setError(getString(R.string.error_invalid_email));
            focusView = mUserEmail;
            cancel = true;
        }
        if (!TextUtils.isEmpty(email) && isValidEmail(email)){

            if (TextUtils.isEmpty(password)) {
                mUserPassword.setError(getString(R.string.error_field_required));
                focusView = mUserPassword;
                cancel = true;
            } else if (!isValidPassword(password)) {
                mUserPassword.setError(getString(R.string.error_invalid_password));
                focusView = mUserPassword;
                cancel = true;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {

            Gson gson = new Gson();
            String userInfoJson = gson.toJson(new UserInfo(email, password));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences.edit().putString(USER_INFO_DATA_KEY,userInfoJson).apply();
            String string = sharedPreferences.getString(USER_INFO_DATA_KEY, "N/A");
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(PREF_EMAIL_KEY, email);
            intent.putExtra("key",string);
            startActivity(intent);
            finish();
        }
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = p.matcher(email);
        return matcher.matches();

    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        return false;
    }
}