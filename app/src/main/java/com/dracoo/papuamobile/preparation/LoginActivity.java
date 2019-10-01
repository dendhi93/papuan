package com.dracoo.papuamobile.preparation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.dracoo.papuamobile.R;
import com.dracoo.papuamobile.Utils.SharedPrefManager;
import com.dracoo.papuamobile.feature.Home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String ResultWS;
    private SharedPrefManager sharedPrefManager;
    @BindView(R.id.etUserName)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
