package com.dracoo.papuamobile.preparation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.dracoo.papuamobile.R;
import com.dracoo.papuamobile.Utils.SharedPrefManager;
import com.dracoo.papuamobile.feature.Home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String ResultWS;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart(){
        super.onStart();
        sharedPrefManager = new SharedPrefManager(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if (sharedPrefManager.getSPUserSudahLogin()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }


}
