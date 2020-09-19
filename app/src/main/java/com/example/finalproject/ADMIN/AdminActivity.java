package com.example.finalproject.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;


/**
 * AdminActivity - this activity will be responsible for allowing the admin to create quiz, assign a quiz or quizzes to students, and look at the student completed quiz grades
 */
public class AdminActivity extends AppCompatActivity {

    private Button assignQuizButton, createQuizButton, logoffButton, gradeReportButton;
    private AnimationDrawable animationOne, animationTwo, animationThree, animationFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        assignQuizButton = findViewById(R.id.assignQuizButton);
        createQuizButton = findViewById(R.id.createQuizButton);
        logoffButton = findViewById(R.id.logoutButton);
        gradeReportButton = findViewById(R.id.gradeReportButton);

        //AnimationDrawables that will be changing the color of the assignQuiz, createQuiz, logOff, and gradeReport buttons
        //Each animation will have different colors
        animationOne = (AnimationDrawable) assignQuizButton.getBackground();
        animationOne.setEnterFadeDuration(2200);
        animationOne.setExitFadeDuration(2200);
        animationTwo = (AnimationDrawable) createQuizButton.getBackground();
        animationTwo.setEnterFadeDuration(2200);
        animationTwo.setExitFadeDuration(2200);
        animationThree = (AnimationDrawable) logoffButton.getBackground();
        animationThree.setEnterFadeDuration(2200);
        animationThree.setExitFadeDuration(2200);
        animationFour = (AnimationDrawable) gradeReportButton.getBackground();
        animationFour.setEnterFadeDuration(2200);
        animationFour.setExitFadeDuration(2200);

        //Listener for the logOff button that will start the log off activity if pressed
        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOff = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logOff);
            }
        });

        //Listener for the createQuiz button that will start the quizLayout activity if pressed
        createQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizLayout = new Intent(getApplicationContext(), QuizLayoutActivity.class);
                startActivity(quizLayout);
            }
        });

        //Listener for the assignQuiz button that will start the assignQuiz activity if pressed
        assignQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assignStudentQuiz = new Intent(getApplicationContext(), AssignStudentQuizActivity.class);
                startActivity(assignStudentQuiz);
            }
        });

        //Listener for the gradeReport button that will start the gradeReport activity if pressed
        gradeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gradeReport = new Intent(getApplicationContext(), AdminGradesActivity.class);
                startActivity(gradeReport);
            }
        });


    }

    @Override
    /**
     * onResume method that will be used to start the color animations of all four buttons if it is not already running
     */
    protected void onResume()
    {
        super.onResume();
        if(animationOne !=null && !animationOne.isRunning())
        {
            animationOne.start();
        }
        if(animationTwo !=null && !animationTwo.isRunning())
        {
            animationTwo.start();
        }
        if(animationThree !=null && !animationThree.isRunning())
        {
            animationThree.start();
        }
        if(animationFour!=null && !animationFour.isRunning())
        {
            animationFour.start();
        }
    }

    @Override
    /**
     * onPause method that will be used to stop the animation of all four buttons if they are currently running
     */
    protected void onPause() {
        super.onPause();
        if (animationOne != null && animationOne.isRunning())
        {
            animationOne.stop();
        }
        if (animationTwo != null && animationTwo.isRunning())
        {
            animationTwo.stop();
        }
        if (animationThree != null && animationThree.isRunning())
        {
            animationThree.stop();
        }
        if (animationFour != null && animationFour.isRunning())
        {
            animationFour.stop();
        }

    }

}