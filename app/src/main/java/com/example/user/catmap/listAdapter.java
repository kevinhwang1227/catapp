package com.example.user.catmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2018-01-07.
 */

public class listAdapter extends BaseAdapter{

    private HashMap<String, Bitmap> imageList;
    private ArrayList<HashMap<String, String>> catList;
    private ViewHolder viewHolder;
    private ArrayList<HashMap<String,String>> orig;

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public listAdapter() {
        imageList = new HashMap<String,Bitmap>();
        catList = new ArrayList<HashMap<String, String>>();
    }

    public listAdapter(HashMap<String, Bitmap> imageList, ArrayList<HashMap<String,String>> catList) {
        this.imageList = imageList;
        this.catList = catList;
    }

    public void setlistAdapter(HashMap<String, Bitmap> imageList, ArrayList<HashMap<String,String>> catList) {
        this.imageList = imageList;
        this.catList = catList;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int i) {
        return catList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.photo);
            viewHolder.textView = (TextView) view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int size = catList.size();
        final HashMap<String,String> elem = catList.get(i);
        /*
        try {
            if (imageList.containsKey(elem.get("name"))) {
                viewHolder.imageView.setImageBitmap(imageList.get(elem.get("name")));
            } else {
                new AsyncTask<URL, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(URL... urls) {
                        try {
                            return BitmapFactory.decodeStream(urls[0].openStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    @Override
                    protected void onPostExecute(Bitmap result) {
                        if (result == null) {
                            viewHolder.imageView.setImageResource(android.R.drawable.ic_menu_report_image);
                        } else {
                            imageList.put(elem.get("name"), result);
                            viewHolder.imageView.setImageBitmap(result);
                            listAdapter.this.notifyDataSetChanged();
                        }
                    }
                }.execute(new URL(Constants.SERVER_URL + "/api/archive/cats/"
                        + "/" + elem.get("name")));
            }
        } catch (Exception e) {
            viewHolder.imageView.setImageResource(android.R.drawable.ic_menu_report_image);
            e.printStackTrace();
        }
*/
        viewHolder.textView.setText("  "+ elem.get("name"));
        viewHolder.imageView.setImageBitmap(imageList.get(elem.get("name")));

        return view;
    }




}
