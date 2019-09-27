package com.papuase.zeerovers.tm.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Activity.AddBarangRusakActivity;
import com.papuase.zeerovers.tm.Adapter.BarangRusakAdapter;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.Model.DataRusakModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataTask;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BarangRusakFragment extends Fragment {

    private static final String TAG = "BarangRusakFragment";

    List<DataRusakModel> dataRusakModels = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    SharedPrefManager sharedPrefManager;
    SharedPrefDataTask sharedPrefDataTask;
    TextView mDataNull;
    ProgressBar mProgress;
    Button mRerty;

    String ResultWS;

    public BarangRusakFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_rusak,container,false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefDataTask = new SharedPrefDataTask(getActivity());

        mDataNull = view.findViewById(R.id.tvDataTerpasangKosongRusak);
        mRerty = view.findViewById(R.id.btnRetryDataRusak);
        mProgress = view.findViewById(R.id.progress_rusak);
        recyclerView = view.findViewById(R.id.listDataRusak);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new BarangRusakAdapter(getActivity(), dataRusakModels);
        recyclerView.setAdapter(mAdapter);

        String id = sharedPrefDataTask.getIDDATABARANGSAVE();
        String vid = sharedPrefDataTask.getVIDDATABARANGSAVE();
        String noTask = sharedPrefDataTask.getNOTASKDATABARANGSAVE();
        Log.i(TAG, "BarangTerpasang: " + noTask);
        Log.i(TAG, "BarangTerpasang: " + id);
        Log.i(TAG, "BarangTerpasang: " + vid);

        FloatingActionButton fab = view.findViewById(R.id.fabAddRusak);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBarangRusakActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(SharedPrefDataTask.VID,vid);
                intent.putExtra(SharedPrefDataTask.ID,id);
                intent.putExtra(SharedPrefDataTask.NoTask,noTask);
                startActivity(intent);
            }
        });

        mRerty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyingBarangRusak().execute();
            }
        });

        new AsyingBarangRusak().execute();

        return view;
    }

    public void clear(){
        dataRusakModels.clear();
        mAdapter.notifyDataSetChanged();
    }

    private class AsyingBarangRusak extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getBarangRusak();

        }

        @Override
        protected String doInBackground(Void ... arg0) {
            return "OK";
        }
    }

    public void getBarangRusak(){
        mProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        String vid = sharedPrefDataTask.getVIDDATABARANGSAVE();
        Log.i(TAG, "BarangRusak: " + vid);
        String url = BaseUrl.getPublicIp + BaseUrl.listBarangRusak+vid;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "BarangRusak: " + url);
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
                                        Log.d("TAG", "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);

                                            DataRusakModel lt = new DataRusakModel(
                                                    data.getString("VID"),
                                                    data.getString("NamaBarang"),
                                                    data.getString("Type"),
                                                    data.getString("SN"),
                                                    data.getString("ESNMODEM"),
                                                    data.getString("Status"),
                                                    data.getString("file_url"),
                                                    data.getString("Description")
                                            );
                                            Log.d("TAG", "getDataTerpasang: " + data);

                                            dataRusakModels.add(lt);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                        mProgress.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                    }else {
                                        String Responsecode = "Data Barang Terpasang Belom Di Buat";
                                        Log.i(TAG, "else Response True: " + Responsecode);
                                        mDataNull.setVisibility(View.VISIBLE);
                                        mRerty.setVisibility(View.GONE);
                                        mDataNull.setText(Responsecode);
                                        mProgress.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "JSONException : " + e.getMessage());
                                    mDataNull.setVisibility(View.VISIBLE);
                                    mRerty.setVisibility(View.VISIBLE);
                                    mDataNull.setText(e.getMessage());
                                    mProgress.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                }

                            }
                        },200);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i("TAG", "onErrorResponse: " + error.getMessage());
                        mDataNull.setVisibility(View.VISIBLE);
                        mRerty.setVisibility(View.VISIBLE);
                        mDataNull.setText(error.getMessage());
                        mProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };

        Mysingleton.getInstance(getActivity()).addToRequestQueue(request);
    }



}
