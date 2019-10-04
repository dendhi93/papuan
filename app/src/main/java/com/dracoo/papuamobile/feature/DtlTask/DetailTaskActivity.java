package com.dracoo.papuamobile.feature.DtlTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dracoo.papuamobile.R;

public class DetailTaskActivity extends AppCompatActivity {
    private static final String TAG = "DataInstallasiActivity";
    Boolean statusIL;
    String ResultWS, id, vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
    }
}
