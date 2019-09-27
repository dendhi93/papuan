package com.papuase.zeerovers.tm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.papuase.zeerovers.tm.Activity.InputSpdGambarActivity;
import com.papuase.zeerovers.tm.Activity.ViewDetailPhotoActivity;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Model.ListSpdGambarModel;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataSPD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ListSpdGambarAdapter extends RecyclerView.Adapter<ListSpdGambarAdapter.ViewHolder> {

    private static final String TAG = "ListSpdGambarAdapter";

    private List<ListSpdGambarModel> listSpdGambarModels;
    Context mContext;
    String replaceAll;

//    SharedPrefDataTask sharedPrefDataTask;

    SharedPrefDataSPD sharedPrefDataSPD;

    public ListSpdGambarAdapter(Context mContext, List<ListSpdGambarModel> listSpdGambarModels) {
        this.listSpdGambarModels = listSpdGambarModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_spd_gambar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        sharedPrefDataSPD = new SharedPrefDataSPD(mContext);

        ListSpdGambarModel data = listSpdGambarModels.get(position);
        viewHolder.mPengunaan.setText(data.getJenisBiaya());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(data.getNominal());
        String getNominal = formatter.format(myNumber);
        viewHolder.mNominal.setText(getNominal);
        viewHolder.mDate.setText(data.getTglInputBiaya());
        viewHolder.mNote.setText(data.getCatatanTransaksi());
        String localFoto = "vsatphase2/";
//        String localFoto = "VsatQC/";
        String getUrl = data.getFile_url();
        replaceAll = getUrl.replaceAll(" ", "");
        Log.i(TAG, "onBindViewHolder: " + BaseUrl.getPublicIp + localFoto + replaceAll);
        Picasso.with(mContext)
                .load(BaseUrl.getPublicIp + localFoto + replaceAll)
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, ViewDetailPhotoActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("ImageSpd", BaseUrl.getPublicIp + localFoto + replaceAll);
                Log.i("TAG", "onClick: " + BaseUrl.getPublicIp +localFoto+ data.getFile_url());
                mContext.startActivity(mIntent);
            }
        });

        viewHolder.myDetailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), InputSpdGambarActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra(SharedPrefDataSPD.VIDSAVE, data.getVID());
                in.putExtra(SharedPrefDataSPD.NOTASKSAVE, data.getNoTask());
                in.putExtra(SharedPrefDataSPD.JenisBiaya, data.getJenisBiaya());
                in.putExtra(SharedPrefDataSPD.Nominal, data.getNominal());
                in.putExtra(SharedPrefDataSPD.TglInputBiaya, data.getTglInputBiaya());
                in.putExtra("note", data.getCatatanTransaksi());
                in.putExtra("idPenguna", data.getId());
                in.putExtra("idFile", data.getFile_id());
                String urlfoto = BaseUrl.getPublicIp + localFoto + replaceAll;
                String nameFoto = urlfoto.replaceAll("http://192.168.25.98:6003/vsatphase2/UploadFoto/",  " ");
//                String nameFoto = urlfoto.replaceAll("http://http://10.10.62.2:7020/VsatQC/UploadFoto/",  " ");
                replaceAll = getUrl.replaceAll(" ", "");
                Log.i(TAG, "onBindViewHolder: " + BaseUrl.getPublicIp + localFoto + replaceAll);
                in.putExtra("nameFoto", nameFoto);
                in.putExtra("urlFoto",urlfoto);
                Log.i(TAG, "onClick jenisBiaya: " + data.getJenisBiaya());
                Log.i(TAG, "onClick nominal: " + data.getNominal());
                Log.i(TAG, "onClick tglInput: " + data.getTglInputBiaya());
                Log.i(TAG, "onClick catatan: " + data.getCatatanTransaksi());
                Log.i(TAG, "onClick nameFoto: " + nameFoto);
                Log.i(TAG, "onClick url: " + urlfoto);
                v.getContext().startActivity(in);
            }
        });

    }



    @Override
    public int getItemCount() {
        return listSpdGambarModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView mPengunaan, mNominal, mDate, mNote;
        LinearLayout myDetailCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewSpd);
            mPengunaan = itemView.findViewById(R.id.tvPengunaan);
            mNominal = itemView.findViewById(R.id.tvNominal);
            mDate = itemView.findViewById(R.id.tvDateSpdCard);
            mNote = itemView.findViewById(R.id.tvNote);
            myDetailCard = itemView.findViewById(R.id.CardDetailSpd);
//            mDeleteItem = itemView.findViewById(R.id.delete_item);

        }
    }
}
