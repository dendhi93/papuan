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
import android.widget.LinearLayout;
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



public class TaskFragment extends Fragment {

    private static final String TAG = "TaskFragment";

    List<ListTaskOpenModel> listTaskOpenModels = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    SharedPrefManager sharedPrefManager;

    TextView mStatusOpen, mCountOpen, mStatusFinish, mCountFinish;
    LinearLayout mOpen, mFinish;

    String jsonStr,ResultWS;
    String responseOpen, responseFinish;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taks,container, false);

        recyclerView = view.findViewById(R.id.rvCardListTask);
        mStatusOpen = view.findViewById(R.id.statusOpen);
        mCountOpen = view.findViewById(R.id.countOpen);
        mStatusFinish = view.findViewById(R.id.statusFinish);
        mCountFinish = view.findViewById(R.id.countFinish);
        mOpen = view.findViewById(R.id.open);
        mFinish = view.findViewById(R.id.finish);

        sharedPrefManager = new SharedPrefManager(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new TaskRecyclerViewAdapter(getActivity(), listTaskOpenModels);
        recyclerView.setAdapter(mAdapter);

        new AsyingTask().execute();
        showFragment(new OpenFragment());

        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new OpenFragment());
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new FinishFragment());
                recyclerView.setVisibility(View.GONE);
            }
        });

        mStatusOpen.setVisibility(View.VISIBLE);
        mCountOpen.setVisibility(View.VISIBLE);
        mStatusFinish.setVisibility(View.VISIBLE);
        mCountFinish.setVisibility(View.VISIBLE);


        return view;
    }

    private void showFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    private class AsyingTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getStatusOpen();
            getStatusFinish();
        }

        @Override
        protected String doInBackground(Void ... arg0) {
            getStatusOpen();
            getStatusFinish();
            return "OK";
        }
    }



    public void getStatusOpen() {
        String name = sharedPrefManager.getSPUserName();
        String nik = sharedPrefManager.getSpNik();
        Log.i("Name", "StatusOpen: " + nik);
        String url = BaseUrl.getPublicIp + BaseUrl.countOpenTask+nik+"/"+name;

        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "getStatusOpen URL: " +url);

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
                                    Log.i(TAG, "jsonObj StatusOpen : " +jsonObj);
                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d("TAG", "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);

                                            Log.i(TAG, "countOpen: " + data.getString("tot"));
                                            Log.i(TAG, "countOpen: " + data.getString("Status"));

                                            Log.d(TAG, "getData: " + data);


                                            final String countOpen = data.getString("tot");
                                            final String statusOpen = data.getString("Status");
                                            mStatusOpen.setText(statusOpen);
                                            mCountOpen.setText(countOpen);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                        mStatusOpen.setVisibility(View.VISIBLE);
                                        mCountOpen.setVisibility(View.VISIBLE);
                                        mStatusFinish.setVisibility(View.VISIBLE);
                                        mCountFinish.setVisibility(View.VISIBLE);

                                    }else {
                                        mStatusOpen.setVisibility(View.VISIBLE);
                                        mCountOpen.setVisibility(View.VISIBLE);
                                        mStatusFinish.setVisibility(View.VISIBLE);
                                        mCountFinish.setVisibility(View.VISIBLE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }
                        }, 20);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
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

    public void getStatusFinish() {
        String name = sharedPrefManager.getSPUserName();
        String nik = sharedPrefManager.getSpNik();
        Log.i("TAG", "getListData: " + name);
        String url = BaseUrl.getPublicIp + BaseUrl.countFinishTask+nik+"/"+name;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }
        Log.i(TAG, "getStatusFinish URL: " +url);

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
                                    Log.i(TAG, "jsonObj StatusFinish : " +jsonObj);
                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d("TAG", "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);

                                            Log.i(TAG, "countFinish: " + data.getString("tot"));
                                            Log.i(TAG, "countFinish: " + data.getString("Status"));

                                            Log.d(TAG, "getData: " + data);


                                            final String countFinish = data.getString("tot");
                                            final String statusFinish = data.getString("Status");
                                            mStatusFinish.setText(statusFinish);
                                            mCountFinish.setText(countFinish);
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }

                                } catch (final JSONException e) {

                                }

                            }
                        }, 20);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
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


}
