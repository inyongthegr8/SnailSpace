package com.celestialsnails.covid19;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.celestialsnails.covid19.coloring.ui.activity.PaintActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_anxiety)
    Button btnAnxiety;
    @BindView(R.id.btn_hotline)
    Button btnHotline;
    @BindView(R.id.btn_space)
    Button btnSpace;
    @BindView(R.id.btn_paint)
    Button btnPaint;
    @BindView(R.id.btn_covid19)
    Button btnBatSoup;
    @BindView(R.id.btn_journal)
    Button btnJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        ButterKnife.bind(this);
        btnAnxiety.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheSunActivity.class);
            startActivity(intent);
        });
        btnHotline.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EmergencyActivity.class);
            startActivity(intent);
        });
        btnSpace.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SpaceActivity.class);
            startActivity(intent);
        });
        btnPaint.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PaintActivity.class);
            startActivity(intent);
        });
        btnBatSoup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BatSoup.class);
            startActivity(intent);
        });
        btnJournal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), JournalActivity.class);
            startActivity(intent);
        });
    }
}
