package com.papuase.zeerovers.tm.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Adapter.TaskRecyclerViewAdapter;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.Model.ListTaskOpenModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OpenFragment extends Fragment {

    private static final String TAG = "OpenFragment";

    List<ListTaskOpenModel> listTaskOpenModels = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    SharedPrefManager sharedPrefManager;
    String jsonStr,ResultWS;

    TextView mDataNull;
    ProgressBar mProgress;
    Button mRerty;


    FrameLayout frameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_open,container, false);



        recyclerView = view.findViewById(R.id.rvCardListOpen);
        sharedPrefManager = new SharedPrefManager(getActivity());

        mProgress = view.findViewById(R.id.progress_open);
        mDataNull = view.findViewById(R.id.tvDataKosongOpen);
        mRerty = view.findViewById(R.id.btnRetryOpen);




        new AsyingTaskList().execute();


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new TaskRecyclerViewAdapter(getActivity(), listTaskOpenModels);
        recyclerView.setAdapter(mAdapter);



        mRerty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListData();
            }
        });

        return view;

    }



    private void showFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_frame, fragment)
                .addToBackStack(null)
                .commit();
    }


    private class AsyingTaskList extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getListData();

        }

        @Override
        protected String doInBackground(Void ... arg0) {
            return "OK";
        }
    }

    public void clear(){
        listTaskOpenModels.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void getListData(){
        mProgress.setVisibility(View.VISIBLE);
        mRerty.setVisibility(View.GONE);
        mDataNull.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        String name = sharedPrefManager.getSPUserName();
        String nik = sharedPrefManager.getSpNik();
        String url = BaseUrl.getPublicIp + BaseUrl.listOpenTask+nik+"/"+name;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "getListData Url: " + url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    JSONObject jsonObj = new JSONObject(response);
                                    String ResultWS = jsonObj.getString("Result");

                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d("TAG", "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);

                                            Log.i("TAG", "id: " + data.getString("ID"));
                                            ListTaskOpenModel lt = new ListTaskOpenModel(
                                                    data.getString("NAMAREMOTE"),
                                                    data.getString("ALAMAT"),
                                                    data.getString("NoTask"),
                                                    data.getString("VID"),
                                                    data.getString("ID"),
                                                    data.getString("TanggalTask"),
                                                    data.getString("PROVINSI"),
                                                    data.getString("idJenisTask"),
                                                    data.getString("NamaKoordinator"),
                                                    data.getString("NamaTeknisi"),
                                                    data.getString("StatusTask")
                                            );

                                            Log.d("TAG", "getData: " + data);

                                            listTaskOpenModels.add(lt);
                                            mProgress.setVisibility(View.GONE);
                                            recyclerView.setVisibility(View.VISIBLE);
                                        }
                                        mAdapter.notifyDataSetChanged();


                                    }else {
                                        mProgress.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
//                                        String Responsecode = jsonObj.getString("Data1");
                                        String Responsecode = "Data Task Tidak Ada";
                                        mDataNull.setVisibility(View.VISIBLE);
                                        mRerty.setVisibility(View.GONE);
                                        mDataNull.setText(Responsecode);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mProgress.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    mDataNull.setVisibility(View.VISIBLE);
                                    mRerty.setVisibility(View.VISIBLE);
                                    mDataNull.setText("Error Connection Catch");
                                }

                            }
                        },200);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        error.getMessage();
                        Log.i("TAG", "onErrorResponse: " + error.getMessage());
                        mDataNull.setVisibility(View.VISIBLE);
                        mRerty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        mDataNull.setText("Cek Jaringan anda");
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
