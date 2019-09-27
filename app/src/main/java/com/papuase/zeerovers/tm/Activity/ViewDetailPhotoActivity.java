package com.papuase.zeerovers.tm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.papuase.zeerovers.tm.R;
import com.squareup.picasso.Picasso;


public class ViewDetailPhotoActivity extends AppCompatActivity {

    private static final String TAG = "ViewDetailPhotoActivity";

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Foto Spd");

        Intent i=getIntent();
        String imageurl=i.getStringExtra("ImageSpd");

        Log.i(TAG, "onCreate: " + imageurl);

        imageView = findViewById(R.id.imageViewDetailSpd);

        Picasso.with(this)
                .load(imageurl)
                .into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
