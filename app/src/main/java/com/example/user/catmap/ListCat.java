package com.example.user.catmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCat extends AppCompatActivity {

    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cat);
        listView = (ListView) findViewById(R.id.catList);
        searchView = (SearchView) findViewById(R.id.searchView);


        final ArrayList<HashMap<String,String>> catlist = new ArrayList<>();
        final HashMap<String,Bitmap> catimg = new HashMap<>();
        HashMap<String,String> a = new HashMap<String,String>();
        HashMap<String,String> b = new HashMap<String,String>();
        HashMap<String,String> c = new HashMap<String,String>();
        a.put("name","야옹이");
        b.put("name","옹옹이");
        c.put("name","캣니스");
        catlist.add(a);
        catlist.add(b);
        catlist.add(c);
        Bitmap abit = BitmapFactory.decodeResource(getResources(),R.drawable.cat1);
        Bitmap bbit = BitmapFactory.decodeResource(getResources(),R.drawable.cat2);
        Bitmap cbit = BitmapFactory.decodeResource(getResources(),R.drawable.cat3);
        catimg.put("야옹이",abit);
        catimg.put("옹옹이",bbit);
        catimg.put("캣니스",cbit);

        listAdapter mlistAdapter = new listAdapter(catimg,catlist);

        listView.setAdapter(mlistAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = catlist.get(i).get("name");
                //String info = catlist.get(i).get("info");
                Bitmap img = catimg.get(name);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                FileOutputStream fo = null;
                try {
                    fo = openFileOutput("cat", Context.MODE_PRIVATE);
                    fo.write(bStream.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(ListCat.this, catInfo.class);
                intent.putExtra("name", name);
                //intent.putExtra("info",info);
                //intent.putExtra("img", byteArray);
                intent.putExtra("from","listView");
                startActivity(intent);

            }
        });
    }




}
