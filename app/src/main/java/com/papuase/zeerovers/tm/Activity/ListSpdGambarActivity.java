package com.papuase.zeerovers.tm.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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
import com.papuase.zeerovers.tm.Adapter.ListSpdGambarAdapter;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.Model.ListSpdGambarModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataSPD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListSpdGambarActivity extends AppCompatActivity {

    private static final String TAG = "ListSpdGambarActivity";

    private List<ListSpdGambarModel> spdGambarModels = new ArrayList<>();
    private FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    TextView mDataKosong;
    Button mRetry;

    String vid, noTask, noTasks, ResultWS;
    ProgressBar mProgress;

    SharedPrefDataSPD sharedPrefDataSPD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_spd_gambar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("SPD");

        sharedPrefDataSPD = new SharedPrefDataSPD(this);

        Intent in = getIntent();
        vid = in.getStringExtra(SharedPrefDataSPD.VIDSAVE);
        noTask = in.getStringExtra(SharedPrefDataSPD.NOTASKSAVE);


        recyclerView = findViewById(R.id.rvCardSpdGbr);
        mProgress = findViewById(R.id.progress_list_spd_gbr);
        mDataKosong = findViewById(R.id.tvDataKosongSpdGbr);
        mRetry = findViewById(R.id.btnRetrySpdGbr);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ListSpdGambarAdapter(getApplicationContext(), spdGambarModels);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fabAddSpdGambar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListSpdGambarActivity.this, InputSpdGambarActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(SharedPrefDataSPD.VIDSAVE, vid);
                intent.putExtra(SharedPrefDataSPD.NOTASKSAVE, noTasks);
                startActivity(intent);
            }
        });

        new AsyingTaskSPD().execute();


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
            return "OK";
        }
    }

    public void clear() {
        spdGambarModels.clear();
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    public void getListSPD(){
        mProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
        String url = BaseUrl.getPublicIp + BaseUrl.listSpdVidGambar+vid;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "Url SPD: "+url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Handler().postDelayed(new Runnable() {
                            @SuppressLint("RestrictedApi")
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

                                            String flag = data.getString("flagconfirm");
                                            Log.i(TAG, "Status flag: " + flag);

                                            if (flag.equals("null")){

                                                ListSpdGambarModel md = new ListSpdGambarModel(
                                                        data.getString("ID"),
                                                        data.getString("file_url"),
                                                        data.getString("Description"),
                                                        data.getString("VID"),
                                                        data.getString("NoTask"),
                                                        data.getString("CatatanTransaksi"),
                                                        data.getString("JenisBiaya"),
                                                        data.getString("Nominal"),
                                                        data.getString("TglInputBiaya"),
                                                        data.getString("file_id")
                                                );

                                                spdGambarModels.add(md);

                                                mProgress.setVisibility(View.GONE);
                                                recyclerView.setVisibility(View.VISIBLE);
                                                fab.setVisibility(View.VISIBLE);
                                            }
                                            else if (flag.equals("true")){
                                                ListSpdGambarModel md = new ListSpdGambarModel(
                                                        data.getString("ID"),
                                                        data.getString("file_url"),
                                                        data.getString("Description"),
                                                        data.getString("VID"),
                                                        data.getString("NoTask"),
                                                        data.getString("CatatanTransaksi"),
                                                        data.getString("JenisBiaya"),
                                                        data.getString("Nominal"),
                                                        data.getString("TglInputBiaya"),
                                                        data.getString("file_id")
                                                );
                                                spdGambarModels.add(md);
                                                mProgress.setVisibility(View.GONE);
                                                recyclerView.setVisibility(View.VISIBLE);
                                                fab.setVisibility(View.GONE);
                                            }
                                            else if (flag.equals("false")){
                                                ListSpdGambarModel md = new ListSpdGambarModel(
                                                        data.getString("ID"),
                                                        data.getString("file_url"),
                                                        data.getString("Description"),
                                                        data.getString("VID"),
                                                        data.getString("NoTask"),
                                                        data.getString("CatatanTransaksi"),
                                                        data.getString("JenisBiaya"),
                                                        data.getString("Nominal"),
                                                        data.getString("TglInputBiaya"),
                                                        data.getString("file_id")
                                                );
                                                spdGambarModels.add(md);
                                                mProgress.setVisibility(View.GONE);
                                                recyclerView.setVisibility(View.VISIBLE);
                                                fab.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }else {
                                        mProgress.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        String Responsecode = "Pengeluaran SPD Tidak Ada";
                                        mDataKosong.setVisibility(View.VISIBLE);
                                        mRetry.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        mDataKosong.setText(Responsecode);
                                        Log.i(TAG, "Response True: " + Responsecode);
                                        fab.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mProgress.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    mDataKosong.setVisibility(View.VISIBLE);
                                    mRetry.setVisibility(View.VISIBLE);
                                    mDataKosong.setText("Cek Jaringan Anda");
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
