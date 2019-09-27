package com.papuase.zeerovers.tm.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.Helper.DatePickerView;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataSPD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class InputSpdGambarActivity extends AppCompatActivity {

    private static final String TAG = "InputSpdGambarActivity";

    String ResultWS;
    SharedPrefDataSPD sharedPrefDataSPD;

    EditText mNoTask, mNoVID, mNamaTask, mNamaRemote, mIpLan, mLokasi, mNominal, mNote, mNameImage;
    DatePickerView mTglKwitansi;
    Spinner mSpinnerPengeluaranSpd;
    ImageView mImageView, mButtonChoose;
    Button mBtnSave, mBtnUpdate, mBtnDelete;


    public static String encodedImage;
    public static String idPenguna;
    public static String idFile;
    public String jenisBiaya;
    public String nominal;
    public String tglInputBiaya;
    String catatanTransaksi;
    String urlFoto;
    public static String replaceAll;
    public static String noTask;
    public static String vid;
    public static String NoVid;
    public static String NoTask;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    private Bitmap bitmap;

    List<String> valueJenisBiaya = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_spd_gambar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Spd Pengeluaran");

        sharedPrefDataSPD = new SharedPrefDataSPD(this);

        NoVid = sharedPrefDataSPD.getVID();
        NoTask = sharedPrefDataSPD.getNoTask();

        mNoTask = findViewById(R.id.no_task_spd);
        mNoVID = findViewById(R.id.no_vid);
        mNamaTask = findViewById(R.id.nama_task_spd);
        mNamaRemote = findViewById(R.id.nama_remote_spd);
        mIpLan = findViewById(R.id.ip_lan_spd);
        mLokasi = findViewById(R.id.lokasi_spd);
        mTglKwitansi = findViewById(R.id.tgl_kwitansi);
        mNominal = findViewById(R.id.nominal_spd);
        mNote = findViewById(R.id.note_pengeluaran);
        mSpinnerPengeluaranSpd = findViewById(R.id.spinnerPengeluaranSpd);
        mImageView = findViewById(R.id.imageViewFile);
        mButtonChoose = findViewById(R.id.chooseImage);
        mNameImage = findViewById(R.id.etImagesName);
        mBtnSave = findViewById(R.id.btnSaveSPD);
        mBtnUpdate = findViewById(R.id.btnUpdateSpd);
        mBtnDelete = findViewById(R.id.btnDeleteSpd);


        mNoTask.setText(sharedPrefDataSPD.getNOTASKSAVE());
        mNoTask.setEnabled(false);
        mNoVID.setText(sharedPrefDataSPD.getVIDSAVE());
        mNoVID.setEnabled(false);
        mNamaTask.setText(sharedPrefDataSPD.getNAMATASKSAVE());
        mNamaTask.setEnabled(false);
        mNamaRemote.setText(sharedPrefDataSPD.getNAMAREMOTESAVE());
        mNamaRemote.setEnabled(false);
        mIpLan.setText(sharedPrefDataSPD.getIPLANSAVE());
        mIpLan.setEnabled(false);
        mLokasi.setText(sharedPrefDataSPD.getLOKASISAVE());
        mLokasi.setEnabled(false);
        mButtonChoose.setVisibility(View.VISIBLE);
        mNameImage.setVisibility(View.VISIBLE);
        mBtnSave.setVisibility(View.VISIBLE);
        mBtnUpdate.setVisibility(View.GONE);
        mBtnDelete.setVisibility(View.GONE);


        Intent in = getIntent();
        vid = in.getStringExtra(SharedPrefDataSPD.VIDSAVE);
        noTask = in.getStringExtra(SharedPrefDataSPD.NOTASKSAVE);

        jenisBiaya = in.getStringExtra(SharedPrefDataSPD.JenisBiaya);
        nominal = in.getStringExtra(SharedPrefDataSPD.Nominal);
        tglInputBiaya = in.getStringExtra(SharedPrefDataSPD.TglInputBiaya);
        catatanTransaksi = in.getStringExtra("note");
        idPenguna = in.getStringExtra("idPenguna");
        idFile = in.getStringExtra("idFile");
        replaceAll = in.getStringExtra("nameFoto");
        urlFoto = in.getStringExtra("urlFoto");



        Log.i(TAG, "intentGetExtra: " + noTask+"/"+ vid);
        Log.i(TAG, "intentGetExtra: " + jenisBiaya+"/"+ nominal +"/"+tglInputBiaya+"/"+ catatanTransaksi +"/"+urlFoto);
        Log.i(TAG, "getIntent jenisBiaya: " + jenisBiaya);
        Log.i(TAG, "getIntent nominal: " + nominal);
        Log.i(TAG, "getIntent tglInputBiaya: " + tglInputBiaya);
        Log.i(TAG, "getIntent catatanTransaksi: " + catatanTransaksi);
        Log.i(TAG, "getIntent urlFoto: " + urlFoto);
        Log.i(TAG, "getIntent idPenguna: " + idPenguna);
        Log.i(TAG, "getIntent idFile: " + idFile);
        Log.i(TAG, "getIntent nameFoto: " + replaceAll);



//        getDataSpdVidInset();
        new AsyingDataSpdVid().execute();
        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSpd();
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aletDelete();
            }
        });


        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsetSpd();
            }
        });

    }

    public void aletDelete(){
       new AlertDialog.Builder(this)
                .setTitle("Hapus Data")
                .setMessage("Apa ada serius Menghapus Data SPD")
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }



    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void setToImageView(Bitmap bitmap) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        byte[] byteFormat = bytes.toByteArray();
        // get the base 64 string
        final String imgString = new String(Base64.encode(byteFormat, Base64.DEFAULT));
        encodedImage = imgString.replaceAll("\\s+", "");
        Log.i("imgStr", "imgStr: " + encodedImage);
//        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos); //bm is the bitmap object



        mImageView.setImageBitmap(decodeBase64(encodedImage));

        Log.d("TAG", "setToImageView: "+encodedImage);

        Log.d("TAG", "setToImageViewDecode: " + decodeBase64(encodedImage));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            Cursor returnCursor =
                    getContentResolver().query(filePath, null, null, null, null);
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String fileName = returnCursor.getString(nameIndex);
                Log.i("TAG", "neme file is : " +fileName);
                mNameImage.setText(fileName);
                Log.i("TAG", "onActivityResult: " + bitmap);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private class AsyingDataSpdVid extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           getDataSpdVid();
           getDataSpdVidPost();
           getListJenisBiaya();

        }

        @Override
        protected String doInBackground(Void ... arg0) {
            return "OK";
        }
    }

    public void getListJenisBiaya(){
        String url = BaseUrl.getPublicIp+BaseUrl.listJenisBiaya;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }else if (url.contains("null")){
            url = url.replace("null",NoTask);
        }
        Log.i(TAG, "listJenisBiaya Url Post: " + url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String ResultWS = jsonObj.getString("Result");
                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d(TAG, "onResponse: " + jsonArray);
                                        valueJenisBiaya = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            Log.i(TAG, "ListjenisBiaya : " + data);
                                            String jenisBiaya = data.getString("jenisBiaya");
                                            Log.i(TAG, "jenisBiaya : " + jenisBiaya);
                                            valueJenisBiaya.add(jenisBiaya);

                                            Log.i(TAG, "arrayValuejenisBiaya: " + valueJenisBiaya);
                                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(InputSpdGambarActivity.this,android.R.layout.simple_spinner_item, valueJenisBiaya);
                                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            mSpinnerPengeluaranSpd.setAdapter(spinnerAdapter);
                                            Log.i(TAG, "spinnerAdapter: " + spinnerAdapter);
                                        }
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "JSONException : " + e.getMessage());

                                }

                            }
                        },200);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());

                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };

        Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public void getDataSpdVidPost(){
        String url = BaseUrl.getPublicIp+BaseUrl.getSpdVid+sharedPrefDataSPD.getNOTASKSAVE()+"/"+sharedPrefDataSPD.getVIDSAVE();
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }else if (url.contains("null")){
            url = url.replace("null",NoTask);
        }
        Log.i(TAG, "getDataSpdVid Url Post: " + url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String ResultWS = jsonObj.getString("Result");
                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d(TAG, "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            Log.i(TAG, "Raw : " + data);
                                            String statusConfirm = data.getString("flagconfirm");
                                            Log.i(TAG, "status : "+statusConfirm);

                                            if (statusConfirm.equals("null")) {

                                                Log.i(TAG, "GetSpdVid : NamaTask = " + data.getString("NamaTask"));
                                                Log.i(TAG, "GetSpdVid : NoTask = " + data.getString("NoTask"));
                                                Log.i(TAG, "GetSpdVid : NAMAREMOTE = " + data.getString("NAMAREMOTE"));
                                                Log.i(TAG, "GetSpdVid : VID = " + data.getString("flagconfirm"));
                                                Log.i(TAG, "GetSpdVid : IPLAN = " + data.getString("VID"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("IPLAN"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("ALAMAT"));

                                                String namaTask = data.getString("NamaTask");
                                                String noTask = data.getString("NoTask");
                                                String noVid = data.getString("VID");
                                                String namaRemote = data.getString("NAMAREMOTE");
                                                String ipLan = data.getString("IPLAN");
                                                String lokasi = data.getString("ALAMAT");

                                                mNoTask.setText(noTask);
                                                mNoTask.setEnabled(false);
                                                mNoVID.setText(noVid);
                                                mNoVID.setEnabled(false);
                                                mNamaTask.setText(namaTask);
                                                mNamaTask.setEnabled(false);
                                                mNamaRemote.setText(namaRemote);
                                                mNamaRemote.setEnabled(false);
                                                mIpLan.setText(ipLan);
                                                mIpLan.setEnabled(false);
                                                mLokasi.setText(lokasi);
                                                mLokasi.setEnabled(false);
//                                                TextView getStr = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
//                                                getStr.setText("");//changes the selected item text to this
//                                                mTglKwitansi.setText(tglInputBiaya);
//                                                mNominal.setText(nominal);
//                                                mNote.setText(catatanTransaksi);
//                                                Picasso.with(getApplicationContext())
//                                                        .load(urlFoto)
//                                                        .into(mImageView);
//                                                replaceAll = urlFoto.replaceAll("http://192.168.25.98:6003/vsatphase2/UploadFoto/",  " ");
//                                                mNameImage.setText(replaceAll);

                                                mButtonChoose.setVisibility(View.VISIBLE);
                                                mNameImage.setVisibility(View.VISIBLE);
                                                mBtnSave.setVisibility(View.VISIBLE);
                                                mBtnUpdate.setVisibility(View.GONE);
                                                mBtnDelete.setVisibility(View.GONE);

                                            }

                                            else if (statusConfirm.equals("false")) {
                                                Log.i(TAG, "GetSpdVid : NamaTask = " + data.getString("NamaTask"));
                                                Log.i(TAG, "GetSpdVid : NoTask = " + data.getString("NoTask"));
                                                Log.i(TAG, "GetSpdVid : NAMAREMOTE = " + data.getString("NAMAREMOTE"));
                                                Log.i(TAG, "GetSpdVid : VID = " + data.getString("flagconfirm"));
                                                Log.i(TAG, "GetSpdVid : IPLAN = " + data.getString("VID"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("IPLAN"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("ALAMAT"));

                                                String namaTask = data.getString("NamaTask");
                                                String noTask = data.getString("NoTask");
                                                String noVid = data.getString("VID");
                                                String namaRemote = data.getString("NAMAREMOTE");
                                                String ipLan = data.getString("IPLAN");
                                                String lokasi = data.getString("ALAMAT");

                                                mNoTask.setText(noTask);
                                                mNoTask.setEnabled(false);
                                                mNoVID.setText(noVid);
                                                mNoVID.setEnabled(false);
                                                mNamaTask.setText(namaTask);
                                                mNamaTask.setEnabled(false);
                                                mNamaRemote.setText(namaRemote);
                                                mNamaRemote.setEnabled(false);
                                                mIpLan.setText(ipLan);
                                                mIpLan.setEnabled(false);
                                                mLokasi.setText(lokasi);
                                                mLokasi.setEnabled(false);

//                                                TextView getStr = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
//                                                getStr.setText("");//changes the selected item text to this
//                                                mTglKwitansi.setText(tglInputBiaya);
//                                                mNominal.setText(nominal);
//                                                mNote.setText(catatanTransaksi);
//                                                Picasso.with(getApplicationContext())
//                                                        .load(urlFoto)
//                                                        .into(mImageView);
//                                                replaceAll = urlFoto.replaceAll("http://192.168.25.98:6003/vsatphase2/UploadFoto/",  " ");
//                                                mNameImage.setText(replaceAll);

                                                mButtonChoose.setVisibility(View.VISIBLE);
                                                mNameImage.setVisibility(View.VISIBLE);
                                                mBtnSave.setVisibility(View.VISIBLE);
                                                mBtnUpdate.setVisibility(View.GONE);
                                                mBtnDelete.setVisibility(View.GONE);
                                            }
                                        }


                                    }else {
                                        String Responsecode = jsonObj.getString("Data1");
                                        Log.i(TAG, "else Response True: " + Responsecode);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "JSONException : " + e.getMessage());

                                }

                            }
                        },200);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());

                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };
        Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    public void getDataSpdVid(){
        String url = BaseUrl.getPublicIp+BaseUrl.getSpdVid+noTask+"/"+vid;
        if (url.contains(" ")){
            url = url.replace(" ","%20");
        }else if (url.contains("null")){
            url = url.replace("null",NoTask);
        }
        Log.i(TAG, "getDataSpdVid Url Adapter: " + url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String ResultWS = jsonObj.getString("Result");
                                    if (ResultWS.equals("True")) {
                                        JSONArray jsonArray = jsonObj.getJSONArray("Raw");
                                        Log.d(TAG, "onResponse: " + jsonArray);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);
                                            Log.i(TAG, "Raw : " + data);
                                            String statusConfirm = data.getString("flagconfirm");
                                            Log.i(TAG, "status : "+statusConfirm);

//                                            if (statusConfirm.equals("null")) {
//
//                                                Log.i(TAG, "GetSpdVid : NamaTask = " + data.getString("NamaTask"));
//                                                Log.i(TAG, "GetSpdVid : NoTask = " + data.getString("NoTask"));
//                                                Log.i(TAG, "GetSpdVid : NAMAREMOTE = " + data.getString("NAMAREMOTE"));
//                                                Log.i(TAG, "GetSpdVid : VID = " + data.getString("flagconfirm"));
//                                                Log.i(TAG, "GetSpdVid : IPLAN = " + data.getString("VID"));
//                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("IPLAN"));
//                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("ALAMAT"));
//
//                                                String namaTask = data.getString("NamaTask");
//                                                String noTask = data.getString("NoTask");
//                                                String noVid = data.getString("VID");
//                                                String namaRemote = data.getString("NAMAREMOTE");
//                                                String ipLan = data.getString("IPLAN");
//                                                String lokasi = data.getString("ALAMAT");
//
//                                                mNoTask.setText(noTask);
//                                                mNoTask.setEnabled(false);
//                                                mNoVID.setText(noVid);
//                                                mNoVID.setEnabled(false);
//                                                mNamaTask.setText(namaTask);
//                                                mNamaTask.setEnabled(false);
//                                                mNamaRemote.setText(namaRemote);
//                                                mNamaRemote.setEnabled(false);
//                                                mIpLan.setText(ipLan);
//                                                mIpLan.setEnabled(false);
//                                                mLokasi.setText(lokasi);
//                                                mLokasi.setEnabled(false);
//                                                TextView getStr = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
//                                                getStr.setText("");//changes the selected item text to this
////                                                mTglKwitansi.setText(tglInputBiaya);
////                                                mNominal.setText(nominal);
////                                                mNote.setText(catatanTransaksi);
////                                                Picasso.with(getApplicationContext())
////                                                        .load(urlFoto)
////                                                        .into(mImageView);
////                                                replaceAll = urlFoto.replaceAll("http://192.168.25.98:6003/vsatphase2/UploadFoto/",  " ");
////                                                mNameImage.setText(replaceAll);
//
//                                                mButtonChoose.setVisibility(View.VISIBLE);
//                                                mNameImage.setVisibility(View.VISIBLE);
//                                                mBtnSave.setVisibility(View.VISIBLE);
//                                                mBtnUpdate.setVisibility(View.GONE);
//                                                mBtnDelete.setVisibility(View.GONE);
//
//                                            }
//
//                                            if (statusConfirm.equals("false")) {
//                                                Log.i(TAG, "GetSpdVid : NamaTask = " + data.getString("NamaTask"));
//                                                Log.i(TAG, "GetSpdVid : NoTask = " + data.getString("NoTask"));
//                                                Log.i(TAG, "GetSpdVid : NAMAREMOTE = " + data.getString("NAMAREMOTE"));
//                                                Log.i(TAG, "GetSpdVid : VID = " + data.getString("flagconfirm"));
//                                                Log.i(TAG, "GetSpdVid : IPLAN = " + data.getString("VID"));
//                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("IPLAN"));
//                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("ALAMAT"));
//
//                                                String namaTask = data.getString("NamaTask");
//                                                String noTask = data.getString("NoTask");
//                                                String noVid = data.getString("VID");
//                                                String namaRemote = data.getString("NAMAREMOTE");
//                                                String ipLan = data.getString("IPLAN");
//                                                String lokasi = data.getString("ALAMAT");
//
//                                                mNoTask.setText(noTask);
//                                                mNoTask.setEnabled(false);
//                                                mNoVID.setText(noVid);
//                                                mNoVID.setEnabled(false);
//                                                mNamaTask.setText(namaTask);
//                                                mNamaTask.setEnabled(false);
//                                                mNamaRemote.setText(namaRemote);
//                                                mNamaRemote.setEnabled(false);
//                                                mIpLan.setText(ipLan);
//                                                mIpLan.setEnabled(false);
//                                                mLokasi.setText(lokasi);
//                                                mLokasi.setEnabled(false);
//
//                                                TextView getStr = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
//                                                getStr.setText("");//changes the selected item text to this
////                                                mTglKwitansi.setText(tglInputBiaya);
////                                                mNominal.setText(nominal);
////                                                mNote.setText(catatanTransaksi);
////                                                Picasso.with(getApplicationContext())
////                                                        .load(urlFoto)
////                                                        .into(mImageView);
////                                                replaceAll = urlFoto.replaceAll("http://192.168.25.98:6003/vsatphase2/UploadFoto/",  " ");
////                                                mNameImage.setText(replaceAll);
//
//                                                mButtonChoose.setVisibility(View.VISIBLE);
//                                                mNameImage.setVisibility(View.VISIBLE);
//                                                mBtnSave.setVisibility(View.VISIBLE);
//                                                mBtnUpdate.setVisibility(View.GONE);
//                                                mBtnDelete.setVisibility(View.GONE);
//                                            }

                                            if (statusConfirm.equals("false")) {
                                                Log.i(TAG, "GetSpdVid : NamaTask = " + data.getString("NamaTask"));
                                                Log.i(TAG, "GetSpdVid : NoTask = " + data.getString("NoTask"));
                                                Log.i(TAG, "GetSpdVid : NAMAREMOTE = " + data.getString("NAMAREMOTE"));
                                                Log.i(TAG, "GetSpdVid : VID = " + data.getString("flagconfirm"));
                                                Log.i(TAG, "GetSpdVid : IPLAN = " + data.getString("VID"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("IPLAN"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("ALAMAT"));

                                                String namaTask = data.getString("NamaTask");
                                                String noTask = data.getString("NoTask");
                                                String noVid = data.getString("VID");
                                                String namaRemote = data.getString("NAMAREMOTE");
                                                String ipLan = data.getString("IPLAN");
                                                String lokasi = data.getString("ALAMAT");

                                                mNoTask.setText(noTask);
                                                mNoTask.setEnabled(false);
                                                mNoVID.setText(noVid);
                                                mNoVID.setEnabled(false);
                                                mNamaTask.setText(namaTask);
                                                mNamaTask.setEnabled(false);
                                                mNamaRemote.setText(namaRemote);
                                                mNamaRemote.setEnabled(false);
                                                mIpLan.setText(ipLan);
                                                mIpLan.setEnabled(false);
                                                mLokasi.setText(lokasi);
                                                mLokasi.setEnabled(false);

//                                                TextView getStr = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
//                                                getStr.setText(jenisBiaya);//changes the selected item text to this

                                                mTglKwitansi.setText(tglInputBiaya);
                                                mNominal.setText(nominal);
                                                mNote.setText(catatanTransaksi);
                                                Log.i(TAG, "urlFoto: "+ urlFoto);
                                                Picasso.with(getApplicationContext())
                                                        .load(urlFoto)
                                                        .into(mImageView);
                                                mNameImage.setText(replaceAll);


                                                mButtonChoose.setVisibility(View.VISIBLE);
                                                mNameImage.setVisibility(View.VISIBLE);
                                                mBtnSave.setVisibility(View.GONE);
                                                mBtnUpdate.setVisibility(View.VISIBLE);
                                                mBtnDelete.setVisibility(View.VISIBLE);
                                            }
                                            else if (statusConfirm.equals("true")) {
                                                Log.i(TAG, "GetSpdVid : NamaTask = " + data.getString("NamaTask"));
                                                Log.i(TAG, "GetSpdVid : NoTask = " + data.getString("NoTask"));
                                                Log.i(TAG, "GetSpdVid : NAMAREMOTE = " + data.getString("NAMAREMOTE"));
                                                Log.i(TAG, "GetSpdVid : VID = " + data.getString("flagconfirm"));
                                                Log.i(TAG, "GetSpdVid : IPLAN = " + data.getString("VID"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("IPLAN"));
                                                Log.i(TAG, "GetSpdVid : ALAMAT = " + data.getString("ALAMAT"));

                                                String namaTask = data.getString("NamaTask");
                                                String noTask = data.getString("NoTask");
                                                String noVid = data.getString("VID");
                                                String namaRemote = data.getString("NAMAREMOTE");
                                                String ipLan = data.getString("IPLAN");
                                                String lokasi = data.getString("ALAMAT");

                                                mNoTask.setText(noTask);
                                                mNoTask.setEnabled(false);
                                                mNoVID.setText(noVid);
                                                mNoVID.setEnabled(false);
                                                mNamaTask.setText(namaTask);
                                                mNamaTask.setEnabled(false);
                                                mNamaRemote.setText(namaRemote);
                                                mNamaRemote.setEnabled(false);
                                                mIpLan.setText(ipLan);
                                                mIpLan.setEnabled(false);
                                                mLokasi.setText(lokasi);
                                                mLokasi.setEnabled(false);

//                                                TextView getStr = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
//                                                getStr.setText(jenisBiaya);//changes the selected item text to this
//                                                getStr.setEnabled(false);
                                                mTglKwitansi.setText(tglInputBiaya);
                                                mTglKwitansi.setEnabled(false);
                                                mNominal.setText(nominal);
                                                mNominal.setEnabled(false);
                                                mNote.setText(catatanTransaksi);
                                                mNote.setEnabled(false);
                                                Log.i(TAG, "urlFoto: "+ urlFoto);
                                                Picasso.with(getApplicationContext())
                                                        .load(urlFoto)
                                                        .into(mImageView);

                                                mButtonChoose.setVisibility(View.GONE);
                                                mNameImage.setVisibility(View.GONE);
                                                mBtnSave.setVisibility(View.GONE);
                                                mBtnUpdate.setVisibility(View.GONE);
                                                mBtnDelete.setVisibility(View.GONE);
                                            }
                                        }


                                    }else {
                                        String Responsecode = jsonObj.getString("Data1");
                                        Log.i(TAG, "else Response True: " + Responsecode);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i(TAG, "JSONException : " + e.getMessage());

                                }

                            }
                        },200);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.i(TAG, "onErrorResponse: " + error.getMessage());

                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Header", "Dota2");
                return headers;
            }
        };
        Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    private void updateSpd() {
        String url = BaseUrl.getPublicIp+BaseUrl.updateSpdVid;
        Log.i("url", "General info: "+ url);

        @SuppressLint("SimpleDateFormat")
        DateFormat dt = new SimpleDateFormat("HH:mm:ss");
        String time = dt.format(Calendar.getInstance().getTime());
        Log.i("Time", "Realtime: " + time);

        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        Log.i("Date", "Realtime Date: " + date);

        String jenisBiyaya = mSpinnerPengeluaranSpd.getSelectedItem().toString();
        String tglKeitansi = mTglKwitansi.getText().toString();
        String nominal = mNominal.getText().toString();
        String note = mNote.getText().toString();
        String fileName = mNameImage.getText().toString();
        String vid = sharedPrefDataSPD.getVID();

        String fildId = idFile;
        Boolean status = true;

        if (tglKeitansi.matches("")) {
            mTglKwitansi.setError("isi data ini");
        }
        else if (jenisBiyaya.matches("")) {
        TextView errorText = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText("isi data ini");//changes the selected item text to this
        }
        else if (nominal.matches("")){
            mNominal.setError("isi data ini");
        }
        else if (note.matches("")){
            mNote.setError("isi data ini");
        }
        else if (fileName.matches("")){
            mNameImage.setError("isi data ini");
        }
        else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String ResultWS = jsonResponse.getString("Result");
                                if (ResultWS.equals("True")){
                                    String Data1 = jsonResponse.getString("Data1");
                                    Toasty.success(InputSpdGambarActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                                    Log.i("TAG", "onResponse: " + Data1);

                                    Log.i("TAG","Ski data from server - ok" + response );

                                    Intent intent = new Intent(getApplicationContext(), ListSpdGambarActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(SharedPrefDataSPD.VIDSAVE, vid);
                                    intent.putExtra(SharedPrefDataSPD.NOTASKSAVE, noTask);
                                    startActivity(intent);
                                    finish();
                                }
                                Log.i("TAG","Ski data from server - ok" + response );

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toasty.error(InputSpdGambarActivity.this, "Failure!", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("TAG","Ski error connect - " + error);
                            Toasty.error(InputSpdGambarActivity.this, "Error!", Toast.LENGTH_SHORT, true).show();
                        }
                    }){

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Header", "Dota2");
                    return headers;
                }

                @Override
                public byte[] getBody() {

                    String str = "{\"Result\": \"True\",\"Raw\": [{\"PARAM2\": [{\"WhereDatabaseinYou\": \"ID='"+idPenguna+"'\",\"JenisBiaya\": \""+jenisBiyaya+"\",\"Nominal\": \""+nominal+"\",\"TglInputBiaya\": \""+tglKeitansi+"\",\"flagtime\":\""+time+"\",\"flagupload\": \""+status+"\",\"CatatanTransaksi\": \""+note+"\"}],\"PARAM1\": [{\"WhereDatabaseinYou\": \"VID = '"+vid+"' and Keterangan = 'Penggunaan Uang SPD' and file_id = '"+fildId+"' \",\"file_url\": \"UploadFoto/"+fileName+"\",\"file_usercreate\": \"admin\",\"flagtime\":\""+time+"\",\"file_datecreate\": \""+date+"\",\"VID\": \""+vid+"\",\"Description\": \""+jenisBiyaya+"\",\"Keterangan\": \"Penggunaan Uang SPD\",\"YourImage64Name\": \""+fileName+"\",\"YourImage64File\": \""+encodedImage+"\"}],\"Data1\": \"\",\"Data2\": \"\",\"Data3\": \"\",\"Data4\": \"\",\"Data5\": \"\",\"Data6\": \"\",\"Data7\": \"\",\"Data8\": \"\",\"Data9\": \"\",\"Data10\": \"\"}]}";
                    Log.i("sendBody", "sendBody: " + str);
                    return str.getBytes();
                }

                public String getBodyContentType()
                {
                    return "application/x-www-form-urlencoded; charset=utf-8";
                }

            };

            Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    private void InsetSpd() {
        String url = BaseUrl.getPublicIp+BaseUrl.insetSpdVid;
        Log.i("url", "General info: "+ url);

        @SuppressLint("SimpleDateFormat")
        DateFormat dt = new SimpleDateFormat("HH:mm:ss");
        String time = dt.format(Calendar.getInstance().getTime());
        Log.i("Time", "Realtime: " + time);

        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        Log.i("Date", "Realtime Date: " + date);

        String jenisBiyaya = mSpinnerPengeluaranSpd.getSelectedItem().toString();
        String tglKeitansi = mTglKwitansi.getText().toString();
        String nominal = mNominal.getText().toString();
        String note = mNote.getText().toString();
        String fileName = mNameImage.getText().toString();
        String namaRemote = mNamaRemote.getText().toString();
        String ipLan = mIpLan.getText().toString();
        String vid = mNoVID.getText().toString();
        String noTask = mNoTask.getText().toString();
        Boolean status = true;

        if (tglKeitansi.matches("")) {
            mTglKwitansi.setError("isi data ini");
        }
        else if (jenisBiyaya.matches("")) {
            TextView errorText = (TextView) mSpinnerPengeluaranSpd.getSelectedView();
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("isi data ini");//changes the selected item text to this
        }
        else if (nominal.matches("")){
            mNominal.setError("isi data ini");
        }
        else if (note.matches("")){
            mNote.setError("isi data ini");
        }
        else if (fileName.matches("")){
            mNameImage.setError("isi data ini");
        }
        else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String ResultWS = jsonResponse.getString("Result");
                                if (ResultWS.equals("True")){
                                    String Data1 = jsonResponse.getString("Data1");
                                    Toasty.success(InputSpdGambarActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                                    Log.i("TAG", "onResponse: " + Data1);

                                    Log.i("TAG","Ski data from server - ok" + response );

                                    Intent intent = new Intent(getApplicationContext(), SpdDetailActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(SharedPrefDataSPD.VIDSAVE, vid);
                                    intent.putExtra(SharedPrefDataSPD.NOTASKSAVE, noTask);
                                    startActivity(intent);
                                    finish();
                                }
                                Log.i("TAG","Ski data from server - ok" + response );

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toasty.error(InputSpdGambarActivity.this, "Failure!", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("TAG","Ski error connect - " + error);
                            Toasty.error(InputSpdGambarActivity.this, "Error!", Toast.LENGTH_SHORT, true).show();
                        }
                    }){

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Header", "Dota2");
                    return headers;
                }

                @Override
                public byte[] getBody() {

                    String str = "{\"Result\": \"True\",\"Raw\": [{\"PARAM1\": [{\"NoTask\": \""+noTask+"\",\"VID\":\""+vid+"\",\"NamaTeknisi\":\"admin\",\"flagtime\":\""+time+"\",\"flagupload\": \""+status+"\",\"JenisBiaya\":\""+jenisBiyaya+"\",\"Nominal\":\""+nominal+"\",\"TglInputBiaya\":\""+tglKeitansi+"\",\"NamaRemote\":\""+namaRemote+"\",\"IPLAN\":\""+ipLan+"\",\"CatatanTransaksi\":\""+note+"\"}],\"PARAM2\": [{\"file_usercreate\":\"admin\",\"file_datecreate\":\""+date+"\",\"flagtime\":\""+time+"\",\"VID\":\""+vid+"\",\"Description\":\""+jenisBiyaya+"\",\"Keterangan\":\"Penggunaan Uang SPD\",\"YourImage64Name\": \""+fileName+"\",\"file_url\":\" UploadFoto/"+fileName+"\",\"YourImage64File\":\""+encodedImage+"\"}],\"Data1\": \"\",\"Data2\": \"\",\"Data3\": \"\",\"Data4\": \"\",\"Data5\": \"\",\"Data6\": \"\",\"Data7\": \"\",\"Data8\": \"\",\"Data9\": \"\",\"Data10\": \"\"}]}";
                    Log.i("sendBody", "sendBody: " + str);
                    return str.getBytes();
                }

                public String getBodyContentType()
                {
                    return "application/x-www-form-urlencoded; charset=utf-8";
                }

            };

            Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    public void delete(){
        String url = BaseUrl.getPublicIp+BaseUrl.deleteSpdVid;
        Log.i("url", "General info: "+ url);
        String vid = sharedPrefDataSPD.getVID();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String ResultWS = jsonResponse.getString("Result");
                                if (ResultWS.equals("True")){
                                    String Data1 = jsonResponse.getString("Data1");
                                    Toasty.success(InputSpdGambarActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                                    Log.i("TAG", "onResponse: " + Data1);

                                    Log.i("TAG","Ski data from server - ok" + response );

                                    Intent intent = new Intent(getApplicationContext(), SpdDetailActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(SharedPrefDataSPD.VIDSAVE, vid);
                                    intent.putExtra(SharedPrefDataSPD.NOTASKSAVE, noTask);
                                    startActivity(intent);
                                    finish();
                                }
                                Log.i("TAG","Ski data from server - ok" + response );

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toasty.error(InputSpdGambarActivity.this, "Failure!", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("TAG","Ski error connect - " + error);
                            Toasty.error(InputSpdGambarActivity.this, "Error!", Toast.LENGTH_SHORT, true).show();
                        }
                    }){

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Header", "Dota2");
                    return headers;
                }

                @Override
                public byte[] getBody() {

                    String str = "{\"Result\": \"True\",\"Raw\": [{\"PARAM1\": [{\"WhereDatabaseinYou\": \"ID = '"+idPenguna+"'\"}],\"PARAM2\": [{\"WhereDatabaseinYou\": \"file_id='"+idFile+"'\"}],\"Data1\": \"\",\"Data2\": \"\",\"Data3\": \"\",\"Data4\": \"\",\"Data5\": \"\",\"Data6\": \"\",\"Data7\": \"\",\"Data8\": \"\",\"Data9\": \"\",\"Data10\": \"\"}]}";
                    Log.i("sendBody", "sendBody: " + str);
                    return str.getBytes();
                }

                public String getBodyContentType()
                {
                    return "application/x-www-form-urlencoded; charset=utf-8";
                }

            };

            Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}


