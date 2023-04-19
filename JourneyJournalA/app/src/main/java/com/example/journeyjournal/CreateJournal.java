package com.example.journeyjournal;

import static com.example.journeyjournal.DBmain.TABLENAME;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class CreateJournal extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    ImageView avatar;
    EditText name, description, date;
    Button submit,display,edit;
    int id=0;

    public static final int CAMERA_REQUEST=100;
    public static final int STORAGE_REQUEST=101;
    String[]cameraPermission;
    String[]storagePermission;

    //for date picker
    EditText mDateFormat;
    DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createjournal);
        cameraPermission=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        dBmain=new DBmain(this);
        findid();
        insertData();
        imagePick();
        editData();


        //for date picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.edit_date);

        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(
                        CreateJournal.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                datePickerDialog.show();


            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                mDateFormat.setText(date);
            }
        };

    }





    private void editData() {
        if (getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle=getIntent().getBundleExtra("userdata");
            id=bundle.getInt("id");
            //for image
            byte[]bytes=bundle.getByteArray("avatar");
            Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            avatar.setImageBitmap(bitmap);
            //for set name
            name.setText(bundle.getString("name"));

            date.setText(bundle.getString("date"));
            //---------------------

            description.setText(bundle.getString("description"));

            //visible edit button and hide submit button
            submit.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
        }
    }

    private void imagePick() {
        avatar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int avatar=0;
                if (avatar==0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        pickFromGallery();
                    }
                }else if (avatar==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission,STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission,CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        boolean result2=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

    private void insertData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("avatar",ImageViewToByte(avatar));
                cv.put("name",name.getText().toString());

                cv.put("date",date.getText().toString());
                //----------------------------
                cv.put("description",description.getText().toString());

                sqLiteDatabase=dBmain.getWritableDatabase();
                Long recinsert=sqLiteDatabase.insert(TABLENAME,null,cv);
                if (recinsert!=null){
                    Toast.makeText(CreateJournal.this, "Journal Inserted successfully", Toast.LENGTH_SHORT).show();
                    //clear when click on submit button
                    avatar.setImageResource(R.drawable.add_photo);
                    name.setText("");
                    date.setText("");
                    //---------------------------------
                    description.setText("");

                    startActivity(new Intent(CreateJournal.this, DisplayData.class));
                }
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateJournal.this,DisplayData.class));
            }
        });
        //for storing new data or update data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("avatar",ImageViewToByte(avatar));
                cv.put("name",name.getText().toString());
                cv.put("description",description.getText().toString());
                cv.put("date",date.getText().toString());
                //---------------------------------------------------------------
                sqLiteDatabase=dBmain.getWritableDatabase();
                long recedit=sqLiteDatabase.update(TABLENAME,cv,"id="+id,null);
                if (recedit!=-1){
                    Toast.makeText(CreateJournal.this, "Update successfully", Toast.LENGTH_SHORT).show();
                    //clear data after submit
                    avatar.setImageResource(R.drawable.add_photo);
                    name.setText("");
                    description.setText("");
                    date.setText("");
                    //---------------------------------------------------
                    //edit hide and submit visible
                    edit.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);

                    startActivity(new Intent(CreateJournal.this, DisplayData.class));

                }
            }
        });
    }

    private byte[] ImageViewToByte(ImageView avatar) {
        Bitmap bitmap=((BitmapDrawable)avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[]bytes=stream.toByteArray();
        return bytes;
    }

    private void findid() {
        avatar=(ImageView)findViewById(R.id.avatar);
        name=(EditText)findViewById(R.id.edit_name);
        date=(EditText)findViewById(R.id.edit_date);
        description=(EditText)findViewById(R.id.edit_description);
        submit=(Button)findViewById(R.id.btn_submit);
        display=(Button)findViewById(R.id.btn_display);
        edit=(Button)findViewById(R.id.btn_edit);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST:{
                if (grantResults.length>0){
                    boolean camera_accept=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storage_accept=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (camera_accept&&storage_accept){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if (grantResults.length>0){
                    boolean storage_accept=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (storage_accept){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
    //overrid method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Uri resultUri=result.getUri();
                Picasso.with(this).load(resultUri).into(avatar);
            }
        }
    }
}