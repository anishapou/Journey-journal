package com.example.journeyjournal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewJournal extends AppCompatActivity {

    ImageView image;
    TextView title, description, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal);

        image=findViewById(R.id.showphoto);
        title=findViewById(R.id.showtitle);
        description=findViewById(R.id.showdescription);
        date=findViewById(R.id.showdate);


        viewData();


    }
    private void viewData() {
        if (getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle=getIntent().getBundleExtra("userdata");
            byte[]bytes=bundle.getByteArray("avatar");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            image.setImageBitmap(bitmap);
            title.setText(bundle.getString("name"));
            description.setText(bundle.getString("description"));
            date.setText(bundle.getString("date"));

        }
    }
}