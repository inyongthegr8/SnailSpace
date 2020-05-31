package com.celestialsnails.covid19.coloring.util;

import android.graphics.Bitmap;

import com.celestialsnails.covid19.images.ColorSearch;


public class BitmapColorSearch extends ColorSearch {
    public BitmapColorSearch(Bitmap bitmap) {
        super(new BitmapConverter(bitmap).getPixelsOfBitmap(), bitmap.getWidth(), bitmap.getHeight());
    }
}
