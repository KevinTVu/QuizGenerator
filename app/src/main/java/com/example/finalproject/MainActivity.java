package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    Animation topAnimation, bottomAnimation;
    ImageView quizImageView;
    TextView quizItUpTextView, slogan, groupMembers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Makes the application full screen instead of displaying the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Calls load animations to store the animations into the animation variables
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Connects the xmls to java
        quizImageView = findViewById(R.id.quizImageView);
        quizItUpTextView = findViewById(R.id.quizItUpTextView);
        //slogan = findViewById(R.id.doingItTheRightWayTextView);
        groupMembers = findViewById(R.id.groupMembersTextView);

        //Sets the animation for the quiz, quizItUp, and slogan image views
        quizImageView.setAnimation(topAnimation);
        quizItUpTextView.setAnimation(bottomAnimation);
        //slogan.setAnimation(bottomAnimation);
        groupMembers.setAnimation(bottomAnimation);

        //Delay that transitions into the LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);


    }




}