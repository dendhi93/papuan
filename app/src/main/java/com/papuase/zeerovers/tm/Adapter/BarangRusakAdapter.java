package com.papuase.zeerovers.tm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Model.DataRusakModel;
import com.papuase.zeerovers.tm.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BarangRusakAdapter extends RecyclerView.Adapter<BarangRusakAdapter.ViewHolder> {

    private static final String TAG = "BarangRusakAdapter";

    private List<DataRusakModel> dataRusakModelList;
    Context context;

    public BarangRusakAdapter(Context context, List<DataRusakModel> dataRusakModelList) {
        this.dataRusakModelList = dataRusakModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_rusak, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        DataRusakModel data = dataRusakModelList.get(position);
        String localFoto = "vsatphase2/";
//        String localFoto = "VsatQC/";

        viewHolder.mDeskripsi.setText(data.getDescription());
        viewHolder.mTitle.setText(data.getNamaBarang());
        viewHolder.mSerialNumber.setText(data.getVid());
        Log.i(TAG, "onBindViewHolderBarangRusak: " + BaseUrl.getPublicIp + localFoto + data.getFileUrl());
        Picasso.with(context)
                .load(BaseUrl.getPublicIp + localFoto + data.getFileUrl())
                .into(viewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return dataRusakModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mSerialNumber, mDeskripsi;
        ImageView mImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageViewBarangRusak);
            mDeskripsi = itemView.findViewById(R.id.deskripsiRusak);
            mTitle = itemView.findViewById(R.id.titleBarangRusak);
            mSerialNumber = itemView.findViewById(R.id.serialNumberRusak);
        }
    }
}
