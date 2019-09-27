package com.papuase.zeerovers.tm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Model.DataTerpasangModel;
import com.papuase.zeerovers.tm.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class BarangTerpasangAdapter extends RecyclerView.Adapter<BarangTerpasangAdapter.ViewHolder> {

    private List<DataTerpasangModel> dataTerpasangModelList;
    Context context;

    public BarangTerpasangAdapter(Context context, List<DataTerpasangModel> dataTerpasangModelList) {
        this.dataTerpasangModelList = dataTerpasangModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_terpasang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        DataTerpasangModel data = dataTerpasangModelList.get(position);
        String localFoto = "vsatphase2/";
//        String localFoto = "VsatQC/";


        viewHolder.mTitle.setText(data.getNamaBarang());
        viewHolder.mVid.setText(data.getVid());
        viewHolder.mSerialNumber.setText(data.getSn());
        viewHolder.mDeskripsi.setText(data.getDescription());
        Picasso.with(context)
                .load(BaseUrl.getPublicIp + localFoto + data.getFileUrl())
                .into(viewHolder.mImageView);


    }

    @Override
    public int getItemCount() {
        return dataTerpasangModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mVid, mSerialNumber, mDeskripsi;
        ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageViewBarang);
            mDeskripsi = itemView.findViewById(R.id.deskripsi);
            mTitle = itemView.findViewById(R.id.titleBarangTerpasang);
            mSerialNumber = itemView.findViewById(R.id.serial_number);
            mVid = itemView.findViewById(R.id.tvVidCard);
        }
    }
}
