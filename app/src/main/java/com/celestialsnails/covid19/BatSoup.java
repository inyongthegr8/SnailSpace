package com.celestialsnails.covid19;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BatSoup extends AppCompatActivity {

    Button button, button2;
    ImageView ImageView2;
    TextView textView4;

    int nImageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batsoup);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        ImageView2 = findViewById(R.id.imageView2);
        textView4 = findViewById(R.id.textView4);

        nImageType = 1;

        displayNumber();
    }

    private void displayNumber()
    {
        if (nImageType == 1)
            ImageView2.setImageResource(R.drawable.bs1);
        else if (nImageType == 2)
            ImageView2.setImageResource(R.drawable.bs2);
        else if (nImageType == 3)
            ImageView2.setImageResource(R.drawable.bs3);
        else if (nImageType == 4)
            ImageView2.setImageResource(R.drawable.bs4);
        else if (nImageType == 5)
            ImageView2.setImageResource(R.drawable.bs5);
        textView4.setText(""+nImageType+"/5");
    }

    public void nextPressed(View v)
    {
        if (nImageType < 5)
            nImageType++;

        displayNumber();
    }

    public void prevPressed(View v)
    {
        if (nImageType > 1)
            nImageType--;

        displayNumber();
    }

}
