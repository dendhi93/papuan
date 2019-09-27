package com.papuase.zeerovers.tm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.papuase.zeerovers.tm.Model.ListInputSpdModel;
import com.papuase.zeerovers.tm.R;

import java.util.List;



public class ListInputSpdAdapter extends RecyclerView.Adapter<ListInputSpdAdapter.Holder> {

    List<ListInputSpdModel> ListInputSpdModels;



    public ListInputSpdAdapter(List<ListInputSpdModel> ListInputSpdModels) {
        this.ListInputSpdModels = ListInputSpdModels;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_upload_photo, viewGroup, false);
        return new Holder(view);
    }


    public void onBindViewHolder(Holder holder, int position) {
        ListInputSpdModel uploadPhoto = ListInputSpdModels.get(position);
        holder.imageView.setImageBitmap(uploadPhoto.getImage());
        holder.mCategory.setText(uploadPhoto.getCategory());
        holder.mNominal.setText("Rp."+uploadPhoto.getNominal());
        holder.mNameImages.setText(uploadPhoto.getNameImages());
    }

    @Override
    public int getItemCount() {
        return ListInputSpdModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView mCategory, mNominal, mNameImages;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivPhotoSpd);
            mCategory = itemView.findViewById(R.id.category);
            mNominal = itemView.findViewById(R.id.nominal);
            mNameImages = itemView.findViewById(R.id.nameImages);
        }
    }
}
