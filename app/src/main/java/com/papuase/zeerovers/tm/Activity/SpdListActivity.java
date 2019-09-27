package com.papuase.zeerovers.tm.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Adapter.SpdListAdapter;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.Model.DetailSpdModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataSPD;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SpdListActivity extends AppCompatActivity {

    private static final String TAG = "SpdListActivity";

    String nikUser,ResultWS;

    private List<DetailSpdModel> detailSpdModels = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    SharedPrefDataSPD sharedPrefDataSPD;
    SharedPrefManager sharedPrefManager;

    TextView mDataKosong;
    Button mRetry;

    String noTask;
    public static String vid;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spd_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Spd Per Vid");
        sharedPrefDataSPD = new SharedPrefDataSPD(this);
        sharedPrefManager = new SharedPrefManager(this);

        Intent in = getIntent();
        noTask = in.getStringExtra(SharedPrefDataSPD.NoTask);

        Log.i(TAG, "NoTask: " + noTask);
        sharedPrefDataSPD.saveSPString(SharedPrefDataSPD.NoTask,noTask);

        nikUser = sharedPrefManager.getSpNik();

        recyclerView = findViewById(R.id.rvCardSpdDetail);
        mProgress = findViewById(R.id.progress_ListSpd);
        mDataKosong = findViewById(R.id.tvDataKosongListSpd);
        mRetry = findViewById(R.id.btnRetryListSpd);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SpdListAdapter(getApplicationContext(), detailSpdModels);
        recyclerView.setAdapter(adapter);

        new AsyingTaskSPD().execute();

    }

    public void clear() {
        detailSpdModels.clear();
        adapter.notifyDataSetChanged();
    }

    private class AsyingTaskSPD extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getListSPD();
        }
        @Override
        protected String doInBackground(Void ... arg0) {
            getListSPD();
            return "OK";
        }
    }

    public void getListSPD(){
        mProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        String url = BaseUrl.getPublicIp + BaseUrl.listSpdVid +noTask;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "Url SPD: "+url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    ResultWS = jsonObj.getString("Result");
                                    clear();
                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d(TAG, "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);

                                            vid = data.getString("VID");

                                            DetailSpdModel md = new DetailSpdModel(
                                                    data.getString("NoTask"),
                                                    data.getString("flagconfirm"),
                                                    data.getString("IPLAN"),
                                                    data.getString("NAMAREMOTE"),
                                                    data.getString("VID"),
                                                    data.getString("TotalPengeluaran")
                                            );

                                            Log.i("modelSpd", "modelSpd: " + md);

                                            detailSpdModels.add(md);

                                            mProgress.setVisibility(View.GONE);
                                            recyclerView.setVisibility(View.VISIBLE);
                                        }
                                        adapter.notifyDataSetChanged();


                                    }else {
                                        mProgress.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        String Responsecode = "Data SPD Tidak Ada";
                                        mDataKosong.setVisibility(View.VISIBLE);
                                        mRetry.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        mDataKosong.setText(Responsecode);
                                        Log.i(TAG, "Response True: " + Responsecode);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mProgress.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    mDataKosong.setVisibility(View.VISIBLE);
                                    mRetry.setVisibility(View.VISIBLE);
                                    mDataKosong.setText("JSONException ERROR");
                                    Log.i(TAG, "JSONException: " + e.getMessage());
                                }

                            }
                        },200);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        error.getMessage();
                        mDataKosong.setVisibility(View.VISIBLE);
                        mRetry.setVisibility(View.VISIBLE);
                        mDataKosong.setText(error.getMessage()    );
                        Log.i("TAG", "onErrorResponse: " + error.getMessage());
                    }
                }){
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
//            onBackPressed();
            Intent in = new Intent(getApplicationContext(), HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
