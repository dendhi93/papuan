package com.papuase.zeerovers.tm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.R;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText mEmail, mNikUser;
    private ShowHidePasswordEditText mNewPassword;
    private Button mbtnSave;
    private String ResultWS;

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Change Password");

        mEmail = findViewById(R.id.email_change_password);
        mNikUser = findViewById(R.id.nik_change_password);
        mNewPassword = findViewById(R.id.new_password_change_password);
        mbtnSave = findViewById(R.id.btnSavePassword);

        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiEmail();
            }
        });


    }

    public void validasiEmail(){
        String email = mEmail.getText().toString();
        String nik = mNikUser.getText().toString();
        String pass = mNewPassword.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(regEx);

        // Match the pattern
        Matcher m = p.matcher(email);

        // First check if email id is not null else show error toast
        if (email.equals("") || email.length() == 0) {

            Toasty.warning(this, "Please enter your Email!", Toast.LENGTH_SHORT, true).show();
        }
        else if (!m.find()) {
            Toasty.warning(this, "Your Email Id is Invalid!", Toast.LENGTH_SHORT, true).show();

        }
        else if (nik.matches("") || nik.length() == 0) {
            mNikUser.setError("Your User Name Empty");
        }
        else if (pass.matches("") || pass.length() == 0) {
            mNewPassword.setError("Your New Password Empty");
        }
        else {
            changePassword();
        }
    }

    public void changePassword(){

        String email = mEmail.getText().toString();
        String nik = mNikUser.getText().toString();
        String pass = mNewPassword.getText().toString();

        String url = BaseUrl.getPublicIp + BaseUrl.changePassword +email+"/"+nik+"/"+pass+"";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ResultWS = object.getString("Result");
                            Log.i("forgetPassword", "Result: " + ResultWS);
                            if (ResultWS.equals("False")) {

                                Log.i("forgetPassword", "Data1: " + object.getString("Data1"));

                                Toasty.success(ChangePasswordActivity.this, "successful..", Toast.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else {
                                Log.i("forgetPassword", "Data1: " + object.getString("Data1"));

                                Toasty.error(ChangePasswordActivity.this, "Failed!!", Toast.LENGTH_SHORT, true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("forgetPassword", "Error: " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }

        };

        Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
