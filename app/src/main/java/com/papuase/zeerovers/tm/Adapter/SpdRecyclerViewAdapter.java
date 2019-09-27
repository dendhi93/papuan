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

import com.papuase.zeerovers.tm.Activity.SpdListActivity;
import com.papuase.zeerovers.tm.Model.ListSpdModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataSPD;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;



public class SpdRecyclerViewAdapter extends RecyclerView.Adapter<SpdRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "SpdRecyclerViewAdapter";
    private List<ListSpdModel> listSpdModels;
    Context context;

    SharedPrefDataSPD sharedPrefDataSPD;

    private int lastPosition = -1;

    // data is passed into the constructor


    public SpdRecyclerViewAdapter(Context context, List<ListSpdModel> listSpdModels) {
        this.listSpdModels = listSpdModels;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_spd_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        ListSpdModel listSpdModel = listSpdModels.get(position);

//        String rupiah = "Rp. ";

        sharedPrefDataSPD = new SharedPrefDataSPD(context);
        if (listSpdModel.getTotal().equals("null")){
            viewHolder.myDiterima.setText("0");
        }else {
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber1 = Double.parseDouble(listSpdModel.getTotal());
            String getTotal = formatter.format(myNumber1);
            viewHolder.myDiterima.setText(getTotal);
        }
        if (listSpdModel.getTotalsuk().equals("null")){
            viewHolder.myDigunakan.setText("0");
        }else {
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber2 = Double.parseDouble(listSpdModel.getTotalsuk());
            String getTotalsuk = formatter.format(myNumber2);
            viewHolder.myDigunakan.setText(getTotalsuk);
        }
        if (listSpdModel.getApprove().equals("null")){
            viewHolder.myDisetujui.setText("0");
        }else {
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber3 = Double.parseDouble(listSpdModel.getApprove());
            String getApprove = formatter.format(myNumber3);
            viewHolder.myDisetujui.setText(getApprove);
        }
        if (listSpdModel.getSisa().equals("null")){
            viewHolder.mySisa.setText("0");
        }else {
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber4 = Double.parseDouble(listSpdModel.getSisa());
            String getSisa = formatter.format(myNumber4);
            viewHolder.mySisa.setText(getSisa);
        }
        if (listSpdModel.getNamaTask().equals("null")){
            viewHolder.myNamaTask.setVisibility(View.GONE);
        }else {
            viewHolder.myNamaTask.setText(listSpdModel.getNamaTask());
        }
        if (listSpdModel.getNoTask().equals("null")){
            viewHolder.myTask.setText("N/A");
        }else {
            viewHolder.myTask.setText(listSpdModel.getNoTask());
        }
        if (listSpdModel.getTanggalTask().equals("null")){
            viewHolder.myDate.setText("N/A");
        }else {
            viewHolder.myDate.setText(listSpdModel.getTanggalTask());
        }
        if (listSpdModel.getNamaTask().equals("null")){
            viewHolder.myNamaTask.setText("N/A");
        }else {
            viewHolder.myNamaTask.setText(listSpdModel.getNamaTask());
        }

        Log.i(TAG, "SPD idTeknisi: " + listSpdModel.getIdTeknisi());
        viewHolder.myBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadein = new AlphaAnimation(0,1);
                fadein.setDuration(50);
                viewHolder.myBtnStart.startAnimation(fadein);
                Intent in = new Intent(v.getContext(), SpdListActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra(SharedPrefDataSPD.NoTask, listSpdModel.getNoTask());
                v.getContext().startActivity(in);
            }
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
        return listSpdModels.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTask, myDate, myDiterima, myDigunakan, myDisetujui, mySisa, myNamaTask;
        Button myBtnStart;

        ViewHolder(View itemView) {
            super(itemView);
            myTask = itemView.findViewById(R.id.tvTaskSpd);
            myDate = itemView.findViewById(R.id.tvDateSpd);
            myDiterima = itemView.findViewById(R.id.uang_diterima);
            myDigunakan = itemView.findViewById(R.id.uang_digunakan);
            myDisetujui = itemView.findViewById(R.id.uang_disetujui);
            mySisa = itemView.findViewById(R.id.uang_sisa);
            myNamaTask = itemView.findViewById(R.id.tvNamaTask);
            myBtnStart = itemView.findViewById(R.id.btnStartSPD);
        }

    }


}
