package com.papuase.zeerovers.tm.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.papuase.zeerovers.tm.Api.BaseUrl;
import com.papuase.zeerovers.tm.Api.Mysingleton;
import com.papuase.zeerovers.tm.R;
import com.papuase.zeerovers.tm.Utils.SharedPrefDataTask;
import com.papuase.zeerovers.tm.Utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class AddBarangRusakActivity extends AppCompatActivity {

    private static final String TAG = "AddBarangRusakActivity";

    public static String encodedImage;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    private Bitmap bitmap;
    private EditText mBarang, mModel, mSerialNumber, mStatus, mCatatan, mFileName;
    private Button mSaveTerpasang;
    private ImageView mImageView;

    String ResultWS;
    Boolean StatusBT;
    SharedPrefManager sharedPrefManager;
    SharedPrefDataTask sharedPrefDataTask;

    public static String vid, id, noTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barang_rusak);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ADD BARANG RUSAK");

        Intent in = getIntent();
        vid = in.getStringExtra(SharedPrefDataTask.VID);
        noTask = in.getStringExtra(SharedPrefDataTask.NoTask);

        mBarang = findViewById(R.id.barang_rusak);
        mModel = findViewById(R.id.model_rusak);
        mSerialNumber = findViewById(R.id.serial_number_rusak);
        mStatus = findViewById(R.id.status_rusak);
        mCatatan = findViewById(R.id.catatan_rusak);
        mSaveTerpasang = findViewById(R.id.btn_simpan_rusak);
        mImageView = findViewById(R.id.imageViewBarangTerpasangRusak);
        mFileName = findViewById(R.id.etImagesNameRusak);

        mSaveTerpasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

    }

    private void insertData() {
        String url = BaseUrl.getPublicIp + BaseUrl.insetBarangRusak;
        Log.i(TAG, "insertData: " + url);
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        String barang = mBarang.getText().toString();
        String model = mModel.getText().toString();
        String sn = mSerialNumber.getText().toString();
        String ipLan = mSerialNumber.getText().toString();
        String nameFile = mFileName.getText().toString();
        String status = mStatus.getText().toString();
//        String catatan = mCatatan.getText().toString();
        Boolean statusBt = StatusBT = true;
//        String vid = sharedPrefManager.getSpVid();
        Log.i(TAG, "insertData: " + vid);
        Log.i(TAG, "insertData: " + statusBt);
        Log.i("GetVID", "updateDataLokasi: " +vid);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            String ResultWS = jsonResponse.getString("Result");

                            if (ResultWS.equals("True")){

                                String Data1 = jsonResponse.getString("Data1");
                                Log.i("TAG", "onResponse: " + Data1);
                                Toasty.success(AddBarangRusakActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(getApplicationContext(), DataBarangActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(SharedPrefDataTask.VID, vid);
                                intent.putExtra(SharedPrefDataTask.NoTask, noTask);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toasty.error(AddBarangRusakActivity.this, "Failed!", Toast.LENGTH_SHORT, true).show();
                        }
                        Log.i("TAG","Ski data from server - ok" + response );
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG","Ski error connect - "+error);
                        Toasty.error(AddBarangRusakActivity.this, "Failed!", Toast.LENGTH_SHORT, true).show();
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
                String str = "{\"Result\": \"True\",\"Raw\": [{\"PARAM1\": [{\"VID\": \""+vid+"\",\"NamaBarang\": \""+barang+"\",\"Type\": \""+model+"\",\"SN\": \""+sn+"\",\"IPlan\": \""+ipLan+"\",\"Status\": \""+status+"\",\"DateCreate\": \""+date+"\",\"UserCreate\": \"BRISAT\"}],\"PARAM3\": [{\"WhereDatabaseinYou\": \"VID='"+vid+"' and NoTask = '"+noTask+"'\",\"FlagDataBarang\": "+statusBt+"}],\"PARAM2\": [{\"file_url\": \"UploadFoto/"+nameFile+"\",\"file_usercreate\": \"admin\",\"file_datecreate\": \""+date+"\",\"VID\": \""+vid+"\",\"Description\": \""+barang+"\",\"Keterangan\": \""+sn+"\",\"YourImage64Name\": \""+nameFile+"\",\"YourImage64File\": \""+encodedImage+"\"}],\"Data1\": \"\",\"Data2\": \"\",\"Data3\": \"\",\"Data4\": \"\",\"Data5\": \"\",\"Data6\": \"\",\"Data7\": \"\",\"Data8\": \"\",\"Data9\": \"\",\"Data10\": \"\"}]}";
                Log.i("str", "strRaw: " + str);
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

    public static Bitmap decodeBase64(String input)
    {
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
//        imageView.setImageBitmap(decoded);

//        Bitmap bm = BitmapFactory.decodeFile("/path/to/image.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos); //bm is the bitmap object
//        byte[] b = baos.toByteArray();

        mImageView.setImageBitmap(decodeBase64(encodedImage));

        Log.d("TAG", "setToImageView: "+encodedImage);

        Log.d("TAG", "setToImageViewDecode: " + decodeBase64(encodedImage));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String fileName;
            Cursor returnCursor =
                    getContentResolver().query(filePath, null, null, null, null);
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                fileName = returnCursor.getString(nameIndex);
                Log.i("TAG", "neme file is : " +fileName);
                mFileName.setText(fileName);
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

    public void choseBarangRusak(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
}
