package com.celestialsnails.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TheSunActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    private TextView mTextMessage;
    FrameLayout exerciseFrame;
    private TextView txtView1;

    // automatically generated
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_sun);

        mTextMessage = (TextView) findViewById(R.id.message);
    //    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    //    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // assign variables
        exerciseFrame = (FrameLayout) findViewById(R.id.exerciseFrame);
        txtView1 = findViewById(R.id.txtView1);
        txtView1.setText("Select an exercise.\n\n\n");
        // setup action listeners
        setUp();

    }

    private void setUp() {

        // action listener for changing exercise button
        Button changeExerciseButton = (Button) findViewById(R.id.changeExerciseButton);
        changeExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createExercisePopupMenu(view);
            }
        });
    }

    private void createExercisePopupMenu(View v) {
        PopupMenu changeExerciseMenu = new PopupMenu(this, v);
        changeExerciseMenu.setOnMenuItemClickListener(this);
        changeExerciseMenu.inflate(R.menu.exercise_menu);
        changeExerciseMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        exerciseFrame.removeAllViews();

        switch ((String) menuItem.getTitle()){
            case "The 555 Method":
             //   Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
                View.inflate(this, R.layout.five_five_five_method, exerciseFrame);
                return true;
            case "The 5-4-3-2-1 Method":
                txtView1.setText ("Find 5 things you see, 4 things you can touch, 3 things you can hear, 2 things you can smell, 1 thing you can taste! (In the photo or your surroundings)");
                View.inflate(this, R.layout.five_four_three_two_one, exerciseFrame);
                drawImage();
                return true;
            case "Breathing Exercise":
                View.inflate(this, R.layout.interactive_breathing_exercise, exerciseFrame);
                txtView1.setText ("Inhale when the sun approaches to hug you.\nExhale when it moves back!\nImagine you're being hugged by the sun.\n");
                breatheExercise();
                return true;
            default:
                return false;
        }
    }


    private void drawImage()
    {
        ImageView clips = findViewById(R.id.clipart);
        clips.setImageResource(R.drawable.clipart);
    }

    private void breatheExercise() {
        ImageView ball = (ImageView) findViewById(R.id.pulsatingBall);
        ball.setImageResource(R.drawable.ssh);

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                ball,
                PropertyValuesHolder.ofFloat("scaleX", 2.0f),
                PropertyValuesHolder.ofFloat("scaleY", 2.0f));
        scaleDown.setDuration(3000);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();
    }
}