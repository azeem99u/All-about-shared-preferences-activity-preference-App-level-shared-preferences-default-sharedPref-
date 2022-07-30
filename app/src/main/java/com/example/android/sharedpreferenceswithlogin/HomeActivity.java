package com.example.android.sharedpreferenceswithlogin;

import static com.example.android.sharedpreferenceswithlogin.LoginActivity.PREF_EMAIL_KEY;
import static com.example.android.sharedpreferenceswithlogin.LoginActivity.USER_INFO_DATA_KEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences settingPref;
    SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener;
    public static final String SWITCH_STATE_KEY = "SWITCH_STATE_KEY";
    private SharedPreferences preferences;
    private SwitchMaterial switchMaterial;
    private View rootView;
    TextView nameTxt, userInfoTxt;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        switchMaterial = findViewById(R.id.dayNighSwitch);
        userInfoTxt = findViewById(R.id.userInfoTxt);
        rootView = findViewById(R.id.rootView);
        nameTxt = findViewById(R.id.nameTxt);
        preferences = getPreferences(MODE_PRIVATE);

        settingPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("user_name")) {
                    String name = sharedPreferences.getString("user_name", "");
                    nameTxt.setText(name);
                }
                if (key.equals("night_switch")) {
                    boolean b = sharedPreferences.getBoolean("night_switch", false);
                    switchMaterial.setChecked(b);
                    if(b){
                        rootView.setBackgroundColor(getResources().getColor(android.R.color.black));
                        switchMaterial.setTextColor(getResources().getColor(android.R.color.white));
                        switchMaterial.setText(" Day");
                        switchMaterial.setButtonDrawable(R.drawable.sun);
                    }else {
                        rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
                        switchMaterial.setTextColor(getResources().getColor(android.R.color.black));
                        switchMaterial.setText(" Night");
                        switchMaterial.setButtonDrawable(R.drawable.moon);

                    }
                }

            }
        };

        settingPref.registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);
        String user_name = settingPref.getString("user_name", "");
        nameTxt.setText(user_name);


        boolean b = settingPref.getBoolean("night_switch", false);
        switchMaterial.setChecked(b);
        if (b){
            rootView.setBackgroundColor(getResources().getColor(android.R.color.black));
            switchMaterial.setTextColor(getResources().getColor(android.R.color.white));
            switchMaterial.setText(" Day");
            switchMaterial.setButtonDrawable(R.drawable.sun);

        }else {
            rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
            switchMaterial.setTextColor(getResources().getColor(android.R.color.black));
            switchMaterial.setText(" Night");
            switchMaterial.setButtonDrawable(R.drawable.moon);
        }
        nameTxt.setText(user_name);


        String userInfoPref = settingPref.getString(USER_INFO_DATA_KEY,"N/A");
        Gson gson = new Gson();
        if(Objects.equals(userInfoPref, "N/A")){
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }else {
            UserInfo userInfo1 = gson.fromJson(userInfoPref, UserInfo.class);
            String userName = userInfo1.getUserName();
            String password = userInfo1.getPassword();
            userInfoTxt.setText("User Name: "+userName+"\nPassword "+password);
        }

        SharedPreferences.Editor editor = App.mySharedPreferences.edit();
        if (getIntent().hasExtra(PREF_EMAIL_KEY)) {
            String email = getIntent().getStringExtra(PREF_EMAIL_KEY);
            Toast.makeText(this, "" + email, Toast.LENGTH_SHORT).show();
            editor.putString(PREF_EMAIL_KEY,email);
            editor.putBoolean(App.LOGIN_STATUS_KEY, true);
            editor.apply();
        }
        if(getIntent().hasExtra("key")){
            String userInfoJson = getIntent().getStringExtra("key");
            Gson gson1 = new Gson();
            UserInfo userInfo = gson1.fromJson(userInfoJson, UserInfo.class);
            String userName2 = userInfo.getUserName();
            String password2 = userInfo.getPassword();
            userInfoTxt.setText("User Name: "+userName2+"\nPassword "+password2);

        }


 //       boolean isSwitch_Checked = preferences.getBoolean(SWITCH_STATE_KEY,false);

        switchMaterial.setChecked(b);
//        if (isSwitch_Checked){
//            rootView.setBackgroundColor(getResources().getColor(android.R.color.black));
//            switchMaterial.setTextColor(getResources().getColor(android.R.color.white));
//            switchMaterial.setText(" Day");
//            switchMaterial.setButtonDrawable(R.drawable.sun);
//        }else {
//            rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
//            switchMaterial.setTextColor(getResources().getColor(android.R.color.black));
//            switchMaterial.setText(" Night");
//            switchMaterial.setButtonDrawable(R.drawable.moon);
//        }
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                preferences.edit().putBoolean(SWITCH_STATE_KEY,isChecked).apply();
                if (isChecked){
                    rootView.setBackgroundColor(getResources().getColor(android.R.color.black));
                    switchMaterial.setText(" Day");
                    switchMaterial.setButtonDrawable(R.drawable.sun);
                    switchMaterial.setTextColor(getResources().getColor(android.R.color.white));
                }else {
                    rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
                    switchMaterial.setText(" Night");
                    switchMaterial.setButtonDrawable(R.drawable.moon);
                    switchMaterial.setTextColor(getResources().getColor(android.R.color.black));

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log,menu);
        return true;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signout){
            App.mySharedPreferences.edit().putBoolean(App.LOGIN_STATUS_KEY,false).apply();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        if (item.getItemId() == R.id.setting){
            startActivity(new Intent(this,SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        settingPref.unregisterOnSharedPreferenceChangeListener(mPreferenceChangeListener);
    }
}