package com.papuase.zeerovers.tm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.papuase.zeerovers.tm.Activity.SpdDetailActivity;
import com.papuase.zeerovers.tm.Model.DetailSpdModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataSPD;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;


public class SpdListAdapter extends RecyclerView.Adapter<SpdListAdapter.ViewHolder>{

    private static final String TAG = "SpdListAdapter";

   private List<DetailSpdModel> detailSpdModels;
   Context mContext;

    private int lastPosition = -1;

    SharedPrefDataSPD sharedPrefDataSPD;
    // data is passed into the constructor


    public SpdListAdapter(Context mContext, List<DetailSpdModel> detailSpdModels) {
        this.detailSpdModels = detailSpdModels;
        this.mContext = mContext;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail_spd_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        sharedPrefDataSPD = new SharedPrefDataSPD(mContext);
       DetailSpdModel detailSpdModel = detailSpdModels.get(position);

        if (detailSpdModel.getFlagconfirm().equals("false")){

            viewHolder.myFlagConfirm.setText("Not Confirmed");
            viewHolder.myButton.setText("Start");
            viewHolder.myFlagConfirm.setBackgroundResource(R.color.super_hot);
        }

       if (detailSpdModel.getFlagconfirm().equals("null")){

           viewHolder.myFlagConfirm.setText("Not Confirmed");
           viewHolder.myButton.setText("Start");
           viewHolder.myFlagConfirm.setBackgroundResource(R.color.super_hot);
       }

        if (detailSpdModel.getFlagconfirm().equals("true")){

            viewHolder.myFlagConfirm.setText("Confirmed");
            viewHolder.myButton.setText("View");
            viewHolder.myFlagConfirm.setBackgroundResource(R.color.colorGreen500);
        }

        if (detailSpdModel.getTotalPengeluaran().equals("null")){
            viewHolder.myTotalPengeluaran.setText("0");
        }else {
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber = Double.parseDouble(detailSpdModel.getTotalPengeluaran());
            String getTotalPengeluaran = formatter.format(myNumber);
            viewHolder.myTotalPengeluaran.setText(getTotalPengeluaran);
        }



        Log.i(TAG, "onBindViewHolder: " + detailSpdModel.getFlagconfirm());

        viewHolder.myNamaRemote.setText(detailSpdModel.getNamaRemote());
        viewHolder.myVid.setText(detailSpdModel.getVid());
        viewHolder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadein = new AlphaAnimation(0,1);
                fadein.setDuration(50);
                viewHolder.myButton.startAnimation(fadein);
                Intent intent = new Intent(v.getContext(), SpdDetailActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(SharedPrefDataSPD.VIDSAVE,detailSpdModel.getVid());
                intent.putExtra(SharedPrefDataSPD.NOTASKSAVE,detailSpdModel.getNoTask());
                v.getContext().startActivity(intent);
            }
        });

        sharedPrefDataSPD.saveSPString(SharedPrefDataSPD.VID,detailSpdModel.getVid());

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
        return detailSpdModels.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myNamaRemote, myVid, myTotalPengeluaran, myFlagConfirm;
        Button myButton;

        ViewHolder(View itemView) {
            super(itemView);
            myNamaRemote = itemView.findViewById(R.id.tvNamaRemoteSpdDetail);
            myVid = itemView.findViewById(R.id.tvVidListSPD);
            myFlagConfirm = itemView.findViewById(R.id.tvFlagConfrim);
            myTotalPengeluaran = itemView.findViewById(R.id.tvTotalPengeluaran);
            myButton = itemView.findViewById(R.id.btnStartSpdDetail);
        }

    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
