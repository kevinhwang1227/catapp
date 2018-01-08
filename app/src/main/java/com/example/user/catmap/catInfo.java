package com.example.user.catmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class catInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_info);
        TextView nametxt = (TextView) findViewById(R.id.name);
        TextView infotxt = (TextView) findViewById(R.id.info);
        ImageView catimage = (ImageView) findViewById(R.id.largeimg) ;

        Intent intent = getIntent();
        String name = (String) intent.getStringExtra("name");
        String from = (String) intent.getStringExtra("from");
        //String info = (String) intent.getStringExtra("info");
        //byte[] byteArray = intent.getByteArrayExtra("img");
        Bitmap img = null;
        try {
            img = BitmapFactory.decodeStream(this.openFileInput("cat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        nametxt.setText(name);
        //infotxt.setText(info);
        catimage.setImageBitmap(img);

        if (from.equals("AddCat")) {

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
