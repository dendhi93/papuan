package com.papuase.zeerovers.tm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.papuase.zeerovers.tm.Activity.TaskDetailActivity;
import com.papuase.zeerovers.tm.Model.ListTaskFinishModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;

import java.util.List;
import java.util.Random;


public class ListFinishRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ListTaskFinishModel> listTaskFinishModels;
    Context context;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    SharedPrefManager sharedPrefManager;

    private int lastPosition = -1;

    // data is passed into the constructor


    public ListFinishRecyclerViewAdapter(Context context, List<ListTaskFinishModel> listTaskFinishModels) {
        this.listTaskFinishModels = listTaskFinishModels;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_finish_list, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

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
        return listTaskFinishModels == null ? 0 : listTaskFinishModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listTaskFinishModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    // stores and recycles views as they are scrolled off screen
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView myTitle, myTask, myDate, myAlamat, myStatus, myLokasi, myOrder, myButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            myTitle = itemView.findViewById(R.id.tvTitleFinish);
            myTask = itemView.findViewById(R.id.tvListFinish);
            myStatus = itemView.findViewById(R.id.tvStatusFinish);
            myDate = itemView.findViewById(R.id.tvDateFinish);
            myAlamat = itemView.findViewById(R.id.tvAlamatFinish);
            myLokasi = itemView.findViewById(R.id.tvLocationFinish);
            myOrder = itemView.findViewById(R.id.tvOrderFinish);
            myButton = itemView.findViewById(R.id.btnStartFinish);
        }

    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        ListTaskFinishModel  listTaskFinishModel = listTaskFinishModels.get(position);
        sharedPrefManager = new SharedPrefManager(context);
        if (listTaskFinishModel.getStatusTask().equals("Finish")){
            viewHolder.myButton.setText("View");
        }else {
            viewHolder.myButton.setText("Start");
        }
        if (listTaskFinishModel.getIdJenisTask().equals("null")){
            viewHolder.myOrder.setVisibility(View.GONE);
        }else {
            viewHolder.myOrder.setText(listTaskFinishModel.getIdJenisTask());
        }
        if (listTaskFinishModel.getNamaRemot().equals("null")){
            viewHolder.myTitle.setText("N/A");
        }else {
            viewHolder.myTitle.setText(listTaskFinishModel.getNamaRemot());
        }
        if (listTaskFinishModel.getNoTask().equals("null")){
            viewHolder.myTask.setText("N/A");
        }else {
            viewHolder.myTask.setText(listTaskFinishModel.getNoTask());
        }
        if (listTaskFinishModel.getTanggalTask().equals("null")){
            viewHolder.myDate.setText("N/A");
        }else {
            viewHolder.myDate.setText(listTaskFinishModel.getTanggalTask());
        }
        if (listTaskFinishModel.getStatusTask().equals("null")){
            viewHolder.myStatus.setText("N/A");
        }else {
            viewHolder.myStatus.setText(listTaskFinishModel.getStatusTask());
        }
        if (listTaskFinishModel.getProvinsi().equals("null")){
            viewHolder.myLokasi.setText("N/A");
        }else {
            viewHolder.myLokasi.setText(listTaskFinishModel.getProvinsi());
        }
        if (listTaskFinishModel.getAlamat().equals("null")){
            viewHolder.myAlamat.setText("N/A");
        }else {
            viewHolder.myAlamat.setText(listTaskFinishModel.getAlamat());
        }
        viewHolder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadein = new AlphaAnimation(0,1);
                fadein.setDuration(50);
                viewHolder.myButton.startAnimation(fadein);
                Intent in = new Intent(v.getContext(), TaskDetailActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra(SharedPrefManager.SP_ID,listTaskFinishModel.getId());
                in.putExtra(SharedPrefManager.SP_VID,listTaskFinishModel.getVid());
                Log.i("ListFinishAdapter", "NoTaskFinish: " + listTaskFinishModel.getId());
                Log.i("ListFinishAdapter", "NoTaskFinish: " + listTaskFinishModel.getVid());
//                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, listTaskFinishModel.getId());
//                sharedPrefManager.saveSPString(SharedPrefManager.SP_VID, listTaskFinishModel.getVid());
                v.getContext().startActivity(in);
            }
        });

        // call Animation function
        setAnimation(viewHolder.itemView, position);


    }

}
