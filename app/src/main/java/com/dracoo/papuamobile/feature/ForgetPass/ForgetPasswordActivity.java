package com.dracoo.papuamobile.feature.ForgetPass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dracoo.papuamobile.Network.BaseUrl;
import com.dracoo.papuamobile.Network.Mysingleton;
import com.dracoo.papuamobile.R;
import com.dracoo.papuamobile.Utils.NetworkUtis;
import com.dracoo.papuamobile.preparation.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ForgetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.registered_emailid)
    EditText ed_registered_emailid;
    NetworkUtis networkUtils;

    private String ResultWS;
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        networkUtils = new NetworkUtis(this);
    }

    @OnClick(R.id.backToLoginBtn)
    void backToLogin(){
        Intent intent = new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.forgot_button)
    void forgot(){
        if (ed_registered_emailid.getText().toString().trim().equals("")){
            Toasty.warning(ForgetPasswordActivity.this, "Please enter your Email!", Toast.LENGTH_SHORT, true).show();
        }else{
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(ed_registered_emailid.getText().toString().trim());

            if (!m.find()){
                Toasty.warning(ForgetPasswordActivity.this, "Your Email Id is Invalid!", Toast.LENGTH_SHORT, true).show();
            }else{
                if (!networkUtils.isConnected()){
                    Toasty.warning(ForgetPasswordActivity.this, "Your Email Id is not connected", Toast.LENGTH_SHORT, true).show();
                }else{
                    String url = BaseUrl.getPublicIp + BaseUrl.forgetPassword + ed_registered_emailid.getText().toString().trim() ;
                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            response -> {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    ResultWS = object.getString("Result");
                                    Log.i("forgetPassword", "Result: " + ResultWS);
                                    if (ResultWS.equals("False")) {

                                        Log.i("forgetPassword", "Data1: " + object.getString("Data1"));
                                        Toasty.success(ForgetPasswordActivity.this, "Check Your Email For New Password..", Toast.LENGTH_SHORT, true).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Log.i("forgetPassword", "Data1: " + object.getString("Data1"));

                                        Toasty.error(ForgetPasswordActivity.this, "Failure!", Toast.LENGTH_SHORT, true).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            },
                            error -> Log.i("forgetPassword", "Error: " + error.getMessage())) {

                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Header", "Dota2");
                            return headers;
                        }

                    };

                    Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                }
            }
        }
    }
}
