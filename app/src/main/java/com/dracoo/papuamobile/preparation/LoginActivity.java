package com.dracoo.papuamobile.preparation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dracoo.papuamobile.Network.BaseUrl;
import com.dracoo.papuamobile.Network.Mysingleton;
import com.dracoo.papuamobile.R;
import com.dracoo.papuamobile.Utils.NetworkUtis;
import com.dracoo.papuamobile.Utils.SharedPrefManager;
import com.dracoo.papuamobile.feature.ForgetPass.ForgetPasswordActivity;
import com.dracoo.papuamobile.feature.Home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String ResultWS;
    private SharedPrefManager sharedPrefManager;
    @BindView(R.id.etUserName)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    private NetworkUtis networkUtis;
    private static final String TAG = "LoginActivity";

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
        networkUtis = new NetworkUtis(this);
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

    @OnClick(R.id.btnLogin)
    void onLogin(){
        if (validateLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim())){
            if (networkUtis.isConnected()){
                pDialog = new ProgressDialog(LoginActivity.this);
                pDialog.setMessage("Loading");
                pDialog.setCancelable(false);

                String url = BaseUrl.getPublicIp + BaseUrl.login +etUsername.getText().toString().trim()
                        +"/"+etPassword.getText().toString().trim()+"";

                Log.i(TAG, "login Url : " + url);
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        response -> {
                            try {
                                JSONObject paramObject = new JSONObject(response);
                                ResultWS = paramObject.getString("Result");
                                Log.i("LoginResault", "Result: "+ResultWS);
                                if (ResultWS.equals("true")){
                                    JSONArray jsonArray = paramObject.getJSONArray("Raw");
                                    Log.i("LoginResault", "Raw + True: "+jsonArray);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Log.i("LoginResault", "Obj Data: " + jsonObject);
                                        Log.i("LoginResault", "Obj UserName: " + jsonObject.getString("UserName"));
                                        Log.i("LoginResault", "Obj Password: " + jsonObject.getString("Password"));
                                        Log.i("LoginResault", "Obj Nama: " + jsonObject.getString("Nama"));
                                        Log.i("LoginResault", "Obj Email: " + jsonObject.getString("Email"));

                                        String nik = jsonObject.getString("UserName");
                                        String nama = jsonObject.getString("Nama");
                                        String email = jsonObject.getString("Email");

                                        Toasty.success(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT, true).show();
                                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_USER_SUDAH_LOGIN, true);
                                        sharedPrefManager.saveSPString(SharedPrefManager.SP_USER_NAME, nama);
                                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, nik);
                                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);

                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }

                                }else {
                                    Toasty.error(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT, true).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.dismiss();
                        },
                        error -> {
                            Toasty.error(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT, true).show();
                            Log.i("TAG", "onResponse: " + error.toString());
                            pDialog.dismiss();
                        }){

                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Header", "Dota2");
                        return headers;
                    }

                };

                Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                pDialog.dismiss();
            }else{
                Toasty.info(this, "handset tidak terhubung dengan internet", Toast.LENGTH_SHORT, true).show();
            }
        }else{
            Toasty.info(this, "Mohon diisi data yang kosong", Toast.LENGTH_SHORT, true).show();
        }
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0 || username.trim().equals("")){
            Toasty.warning(LoginActivity.this, "Username is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        }
        if(password == null || password.trim().length() == 0 || password.trim().equals("")){
            Toasty.warning(LoginActivity.this, "Password is required!", Toast.LENGTH_SHORT, true).show();

            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_loginForgot)
    void forgetPass(){
        Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.tv_loginSignUp)
    void onSinUp(){
        Toasty.info(this, "Contact Admin", Toast.LENGTH_SHORT, true).show();
    }


}
