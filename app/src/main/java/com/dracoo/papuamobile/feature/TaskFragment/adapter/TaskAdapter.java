package com.dracoo.papuamobile.feature.TaskFragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dracoo.papuamobile.Model.ListTaskOpenModel;
import com.dracoo.papuamobile.R;
import com.dracoo.papuamobile.Utils.SharedPrefDataTask;
import com.dracoo.papuamobile.Utils.SharedPrefManager;
import com.dracoo.papuamobile.feature.DtlTask.DetailTaskActivity;

import java.util.List;
import java.util.Random;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<ListTaskOpenModel> listTaskOpenModels;
    Context context;


    private static final String TAG = "TaskRecyclerViewAdapter";

    private int lastPosition = -1;
    SharedPrefManager sharedPrefManager;

    // data is passed into the constructor


    public TaskAdapter(Context context, List<ListTaskOpenModel> listTaskOpenModels) {
        this.context = context;
        this.listTaskOpenModels = listTaskOpenModels;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_task, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        ListTaskOpenModel mListTaskOpenModel = listTaskOpenModels.get(position);
        sharedPrefManager = new SharedPrefManager(context);

        Log.i(TAG, "OrderBy: " + mListTaskOpenModel.getIdJenisTask());

        if (mListTaskOpenModel.getIdJenisTask().equals("null") || mListTaskOpenModel.getIdJenisTask().equals("")){
            viewHolder.mOrder.setVisibility(View.GONE);
        }else {
            viewHolder.mOrder.setText(mListTaskOpenModel.getIdJenisTask());
        }
        if (mListTaskOpenModel.getNamaRemote().equals("null")){
            viewHolder.myTitle.setText("N/A");
        }else {
            viewHolder.myTitle.setText(mListTaskOpenModel.getNamaRemote());
        }
        if (mListTaskOpenModel.getNoTask().equals("null")){
            viewHolder.myTask.setText("N/A");
        }else {
            viewHolder.myTask.setText(mListTaskOpenModel.getNoTask());
        }
        if (mListTaskOpenModel.getTanggalTask().equals("null")){
            viewHolder.myDate.setText("N/A");
        }else {
            viewHolder.myDate.setText(mListTaskOpenModel.getTanggalTask());
        }
        if (mListTaskOpenModel.getStatusTask().equals("null")){
            viewHolder.myStatus.setText("N/A");
        }else {
            viewHolder.myStatus.setText(mListTaskOpenModel.getStatusTask());
        }
        if (mListTaskOpenModel.getProvinsi().equals("null")){
            viewHolder.myProvinsi.setText("N/A");
        }else {
            viewHolder.myProvinsi.setText(mListTaskOpenModel.getProvinsi());
        }
        if (mListTaskOpenModel.getAlamat().equals("null")){
            viewHolder.mAlamat.setText("N/A");
        }else {
            viewHolder.mAlamat.setText(mListTaskOpenModel.getAlamat());
        }
        Log.i(TAG, "VID per Card: " + mListTaskOpenModel.getId());
        viewHolder.myButton.setOnClickListener(v -> {
            Animation fadein = new AlphaAnimation(0,1);
            fadein.setDuration(50);
            viewHolder.myButton.startAnimation(fadein);
            Intent in = new Intent(v.getContext(), DetailTaskActivity.class);
            in.putExtra(SharedPrefManager.SP_ID, mListTaskOpenModel.getId());
            in.putExtra(SharedPrefDataTask.VID, mListTaskOpenModel.getVid());
            in.putExtra(SharedPrefDataTask.NoTask, mListTaskOpenModel.getNoTask());
            Log.i("ListOpenAdapter", "NoTaskOpen: " + mListTaskOpenModel.getId());
            Log.i("ListOpenAdapter", "NoTaskOpen: " + mListTaskOpenModel.getVid());
            Log.i("ListOpenAdapter", "NoTaskOpen: " + mListTaskOpenModel.getNoTask());
//               sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, mListTaskOpenModel.getId());
//               sharedPrefManager.saveSPString(SharedPrefManager.SP_VID, mListTaskOpenModel.getVid());
            v.getContext().startActivity(in);
        });

        // call Animation function
        setAnimation(viewHolder.itemView, position);
    }

    // Animation appear with you scroll down only
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.INFINITE, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(1001));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return listTaskOpenModels.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTitle, myTask, myDate, mAlamat, myStatus, myProvinsi, mOrder, myButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTitle = itemView.findViewById(R.id.tvTitleTask);
            myTask = itemView.findViewById(R.id.tvListTask);
            myDate = itemView.findViewById(R.id.tvDateTask);
            mAlamat = itemView.findViewById(R.id.tvAlamatTask);
            myStatus = itemView.findViewById(R.id.tvStatusTask);
            myProvinsi = itemView.findViewById(R.id.tvLocationTask);
            mOrder = itemView.findViewById(R.id.tvOrderTask);
            myButton = itemView.findViewById(R.id.btnStartTask);
        }

    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
