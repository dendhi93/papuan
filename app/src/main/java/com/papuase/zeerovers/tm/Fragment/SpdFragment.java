package com.papuase.zeerovers.tm.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Adapter.SpdRecyclerViewAdapter;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.Model.ListSpdModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataTask;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpdFragment extends Fragment {

    private static final String TAG = "SpdFragment";

    List<ListSpdModel> listSpdModels = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    SharedPrefManager sharedPrefManager;
    SharedPrefDataTask sharedPrefDataTask;
    TextView mDiBerikan, mPresetujuan, mDiGunakan, mSisa;

    LinearLayout mLinearLayout;


    TextView mDataNull;
    ProgressBar mProgress;
    Button mRerty;

    String nikUser;

    public SpdFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spd,container, false);
        mDiBerikan = view.findViewById(R.id.tvDiBerikan);
        mPresetujuan = view.findViewById(R.id.tvPresetujuan);
        mDiGunakan = view.findViewById(R.id.tvDiGunakan);
        mSisa = view.findViewById(R.id.tvSisa);
        recyclerView = view.findViewById(R.id.rvCardVertical);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefDataTask = new SharedPrefDataTask(getActivity());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new AsyingTaskSPD().execute();
        recyclerView.setHasFixedSize(true);
        mAdapter = new SpdRecyclerViewAdapter(getActivity(),listSpdModels );
        recyclerView.setAdapter(mAdapter);
        mLinearLayout = view.findViewById(R.id.llHorizontalScroll);
        mProgress = view.findViewById(R.id.progress_spd);

        nikUser = sharedPrefManager.getSpNik();

        mDataNull = view.findViewById(R.id.tvDataKosongSpd);
        mRerty = view.findViewById(R.id.btnRetrySpd);

        mRerty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTaskSPD();
            }
        });


        return view;
    }

    public void clear(){
        listSpdModels.clear();
        mAdapter.notifyDataSetChanged();
    }



    private class AsyingTaskSPD extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getTotalUang();
            getTaskSPD();
        }

        @Override
        protected String doInBackground(Void ... arg0) {
            return "OK";
        }
    }

    public void getTaskSPD(){
        mProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        String url = BaseUrl.getPublicIp + BaseUrl.listSpd+nikUser;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "Url SPD: "+url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.i(TAG, "jsonObj TaskSPD: " +jsonObj);
                            String ResultWS = jsonObj.getString("Result");
                            clear();
                            if (ResultWS.equals("True")) {
                                JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                Log.d(TAG, "onResponse: " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    ListSpdModel lt = new ListSpdModel(
                                            data.getString("NoTask"),
                                            data.getString("NamaTask"),
                                            data.getString("NamaTeknisi"),
                                            data.getString("IdTeknisi"),
                                            data.getString("TypeTeknisi"),
                                            data.getString("total"),
                                            data.getString("approve"),
                                            data.getString("TanggalTask"),
                                            data.getString("totalsuk"),
                                            data.getString("sisa")
                                    );

                                    Log.d(TAG, "getDataSpd: " + data);
                                    Log.d(TAG, "getDataSpd: " + data.getString("total"));
                                    Log.d(TAG, "getDataSpd: " + data.getString("approve"));
                                    Log.d(TAG, "getDataSpd: " + data.getString("totalsuk"));
                                    Log.d(TAG, "getDataSpd: " + data.getString("sisa"));




                                    listSpdModels.add(lt);
                                    mProgress.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                                mAdapter.notifyDataSetChanged();


                            }else {
                                mProgress.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                String Responsecode = "Data SPD Tidak Ada";
                                mDataNull.setVisibility(View.VISIBLE);
                                mRerty.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                mDataNull.setText(Responsecode);
                                Log.i(TAG, "Response True: " + Responsecode);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            mDataNull.setVisibility(View.VISIBLE);
                            mRerty.setVisibility(View.VISIBLE);
                            mDataNull.setText("JSONException ERROR");
                            Log.i(TAG, "JSONException: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        error.getMessage();
                        mDataNull.setVisibility(View.VISIBLE);
                        mRerty.setVisibility(View.VISIBLE);
                        mDataNull.setText("Cek Jaringan anda");
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

        Mysingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void getTotalUang(){
        String url = BaseUrl.getPublicIp + BaseUrl.diberikan+nikUser;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "getTotalUang URL: " + url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {
                            JSONObject jsonObj = new JSONObject(response1);
                            Log.i(TAG, "jsonObj TotalUang: " + jsonObj);
                            String ResultWS = jsonObj.getString("Result");

                            if (ResultWS.equals("True")) {
                                JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                Log.d(TAG, "onResponse: " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    Log.i(TAG, "total: " + data.getString("total"));

                                    Log.d(TAG, "TotalUang: " + data.getString("total"));

                                    final String diBerikan = data.getString("total");
                                    NumberFormat formatter = new DecimalFormat("#,###");
                                    double myNumber1 = Double.parseDouble(diBerikan);
                                    String diBerikan1 = formatter.format(myNumber1);
                                    mDiBerikan.setText(diBerikan1);
                                    getTotalApprove();
                                }

                            }else {
                                String Responsecode = jsonObj.getString("Data1");
                                Log.i(TAG, "Response True: " + Responsecode);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "JSONException: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };
        Mysingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void getTotalApprove(){
        String req = "REQ";
        String url = BaseUrl.getPublicIp + BaseUrl.persetujuan+nikUser;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "getTotalApprove URL: " + url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response2) {
                        try {
                            JSONObject jsonObj = new JSONObject(response2);
                            Log.i(TAG, "jsonObj TotalApprove: " + jsonObj);
                            String ResultWS = jsonObj.getString("Result");

                            if (ResultWS.equals("True")) {
                                JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                Log.d(TAG, "onResponse: " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    Log.i(TAG, "total: " + data.getString("total"));
                                    Log.d(TAG, "TotalApprove: " + data.getString("total"));
                                    final String presetujuan = data.getString("total");
                                    NumberFormat formatter = new DecimalFormat("#,###");
                                    double myNumber2 = Double.parseDouble(presetujuan);
                                    String presetujuan1 = formatter.format(myNumber2);
                                    mPresetujuan.setText(presetujuan1);
                                    getTotalPenggunaan();
                                }

                            }else {
                                String Responsecode = jsonObj.getString("Data1");
                                Log.i(TAG, "Response True: " + Responsecode);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "JSONException: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };
        Mysingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void getTotalPenggunaan(){
        String url = BaseUrl.getPublicIp + BaseUrl.digunakan+nikUser;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "Url TotalPenggunaan: " + url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response3) {
                        try {
                            JSONObject jsonObj = new JSONObject(response3);
                            Log.i(TAG, "jsonObj TotalPenggunaan: " + jsonObj);
                            String ResultWS = jsonObj.getString("Result");
                            if (ResultWS.equals("True")) {
                                JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                Log.d(TAG, "onResponse: " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    Log.i("TAG", "total: " + data.getString("totalsuk"));
                                    Log.d("TAG", "TotalPenggunaan: " + data.getString("totalsuk"));
                                    final String diGunakan = data.getString("totalsuk");
                                    NumberFormat formatter = new DecimalFormat("#,###");
                                    double myNumber3 = Double.parseDouble(diGunakan);
                                    String diGunakan1 = formatter.format(myNumber3);
                                    mDiGunakan.setText(diGunakan1);
                                    getSisaUang();
                                }

                            }else {
                                String Responsecode = jsonObj.getString("Data1");
                                Log.i(TAG, "Response True: " + Responsecode);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "JSONException: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };
        Mysingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void getSisaUang(){
        String req = "REQ";
        String url = BaseUrl.getPublicIp + BaseUrl.sisa+nikUser;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "getSisaUang URL : "+ url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response4) {
                        try {
                            JSONObject jsonObj = new JSONObject(response4);
                            Log.i(TAG, "jsonObj getSisaUang: " + jsonObj);
                            String ResultWS = jsonObj.getString("Result");

                            if (ResultWS.equals("True")) {
                                JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                Log.d(TAG, "onResponse: " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    Log.i(TAG, "total: " + data.getString("SISA"));
                                    Log.d(TAG, "SisaUang: " + data.getString("SISA"));
                                    final String sisa = data.getString("SISA");
                                    if (data.getString("SISA").equals("null")){
                                        mSisa.setText("0");
                                    }else {
                                        NumberFormat formatter = new DecimalFormat("#,###");
                                        double myNumber4 = Double.parseDouble(sisa);
                                        String sisa1 = formatter.format(myNumber4);
                                        mSisa.setText(sisa1);
                                    }

                                }

                            }else {
                                String Responsecode = jsonObj.getString("Data1");
                                Log.i(TAG, "Response True: " + Responsecode);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "JSONException: " + e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };

        Mysingleton.getInstance(getContext()).addToRequestQueue(request);
    }

}
