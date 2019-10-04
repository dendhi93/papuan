package com.dracoo.papuamobile.feature.TaskFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dracoo.papuamobile.Model.ListTaskOpenModel;
import com.dracoo.papuamobile.R;
import com.dracoo.papuamobile.Utils.SharedPrefManager;
import com.dracoo.papuamobile.feature.TaskFragment.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TaskFragment extends Fragment {

    @BindView(R.id.rvCardListTask)
    RecyclerView rvCardListTask;
    @BindView(R.id.statusOpen)
    TextView statusOpen;
    @BindView(R.id.countOpen)
    TextView countOpen;
    @BindView(R.id.statusFinish)
    TextView statusFinish;
    @BindView(R.id.countFinish)
    TextView countFinish;
    @BindView(R.id.open)
    LinearLayout open;
    @BindView(R.id.finish)
    LinearLayout finish;

    String jsonStr,ResultWS;
    String responseOpen, responseFinish;
    private static final String TAG = "TaskFragment";
    List<ListTaskOpenModel> listTaskOpenModels = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    SharedPrefManager sharedPrefManager;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCardListTask.setLayoutManager(layoutManager);
        rvCardListTask.setHasFixedSize(true);
        mAdapter = new TaskAdapter(getActivity(), listTaskOpenModels);
        rvCardListTask.setAdapter(mAdapter);
    }

    @OnClick(R.id.open)
    void onOpen(){
        rvCardListTask.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.finish)
    void onFinish(){
        rvCardListTask.setVisibility(View.GONE);
    }


}
