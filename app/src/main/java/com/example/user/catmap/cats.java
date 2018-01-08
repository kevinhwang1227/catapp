package com.example.user.catmap;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2018-01-08.
 */

public class cats {
    private HashMap<String, Bitmap> image;
    private ArrayList<HashMap<String,String>> cats;

    public cats(HashMap<String, Bitmap> image, ArrayList<HashMap<String,String>> cats) {
        this.image = image;
        this.cats = cats;
    }

    public HashMap<String, Bitmap> getImage() {
        return image;
    }

    public ArrayList<HashMap<String,String>> getCats() {
        return cats;
    }
}
