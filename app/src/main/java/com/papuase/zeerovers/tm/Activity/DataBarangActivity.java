package com.papuase.zeerovers.tm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.papuase.zeerovers.tm.Adapter.SectionsPageAdapter;
import com.papuase.zeerovers.tm.Fragment.BarangRusakFragment;
import com.papuase.zeerovers.tm.Fragment.BarangTerpasangFragment;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataTask;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;


public class DataBarangActivity extends AppCompatActivity {

    private static final String TAG = "DataBarangActivity";

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    String id, vid, noTask;

    SharedPrefDataTask sharedPrefDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("DATA BARANG");

        sharedPrefDataTask = new SharedPrefDataTask(this);

        id = sharedPrefDataTask.getIDDATABARANGSAVE();
        vid = sharedPrefDataTask.getVIDDATABARANGSAVE();
        noTask = sharedPrefDataTask.getNOTASKDATABARANGSAVE();
        Log.i(TAG, "id: " +id);
        Log.i(TAG, "vid: "+vid);
        Log.i(TAG, "notask: "+noTask);




        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BarangTerpasangFragment(), "TERPASANG");
        adapter.addFragment(new BarangRusakFragment(), "RUSAK");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
//            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(SharedPrefManager.SP_ID, id);
            intent.putExtra(SharedPrefDataTask.VID, vid);
            intent.putExtra(SharedPrefDataTask.NoTask, noTask);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
