package com.celestialsnails.covid19;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.net.Uri;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class GifImageView extends SurfaceView {

    SurfaceHolder surfaceHolder;
    ImageView sunImage;

    public GifImageView(Context context) {
        super(context);
    }

    TextView level_text;
    LinearLayout ly;

    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        start();
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        start();
    }

    public void start() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

}