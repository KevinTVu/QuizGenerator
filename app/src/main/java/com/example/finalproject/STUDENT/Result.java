package com.example.finalproject.STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.Student;

import java.util.Locale;

public class Result extends AppCompatActivity {

    // global variables textview
    private TextView title;         // for title
    private TextView resultView;    // for result container
    private TextView timeTitle;     // for time title
    private TextView timeResult;    // for time result

    // global variables buttons
    private Button backToMenu;      // for back to main menu
    private Button viewAnswers;     // for viewing quiz answers with questions
    private Button logout;          // logout button

    // declare database
    private DatabaseHelper db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // set all textviews to each key from xml file
        title = findViewById(R.id.title);
        resultView = findViewById(R.id.result);
        timeTitle = findViewById(R.id.completeTimeTitle);
        timeResult = findViewById(R.id.timeCount);

        // set all buttons to each key from xml file
        backToMenu = findViewById(R.id.back_to_menu);
        viewAnswers = findViewById(R.id.view_history);
        logout = findViewById(R.id.logout);

        // set the database from the system, contained all the data
        db = new DatabaseHelper(this);

        // receiving all the data from the sender activity
        int correct = getIntent().getIntExtra("Correct", 0);
        int wrong = getIntent().getIntExtra("Wrong", 0);
        int timeCompleted_inSec = getIntent().getIntExtra("TimeCompleted", 0) - 1;   // make it correct
        int totalQuestion = getIntent().getIntExtra("TotalQuestions", 0);

        // convert time from milliseconds to minutes
        int minutes = timeCompleted_inSec / 60;
        int second = timeCompleted_inSec % 60;

        // format the time in string
        String timeFormat = String.format(Locale.getDefault(),
                "%01d min : %01d sec", minutes, second);

        // set the title for the textview
        title.setText("Score Result.");

        // calculate the percentage of the quiz result
        double percentage = (correct / (double)totalQuestion) * 100;

        // calculate the grade depends on the percentage the user got
        String grade;
        if(percentage >= 90)
        {
            grade = "A";
        }
        else if(percentage >= 80)
        {
            grade = "B";
        }
        else if(percentage >= 70)
        {
            grade = "C";
        }
        else if(percentage >= 60)
        {
            grade = "D";
        }
        else
        {
            grade = "F";
        }
        // record the grade into the database system
        db.updateGrades(QuizAnswersUtil.username, QuizAnswersUtil.quizID, percentage, grade);

        // format the percentage to 1 decimal place
        @SuppressLint("DefaultLocale") String strDouble = String.format("%.1f", percentage);
        // set the information to be display on the result textview
        resultView.setText("Correct: " + correct + "\n" +
                "Wrong  : " + wrong + "\n" +
                "Score  : " + strDouble + "%\n\n");
        timeTitle.setText("Completed In");
        timeResult.setText(timeFormat);

        /* <<<<<<<<<< Menu >>>>>>>>>>*/
        // back to main menu
        backToMenu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Student.class);
                startActivity(intent);
            }
        });

        // view questions with answers
        viewAnswers.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizAnswersActivity.class);
                startActivity(intent);
            }
        });

        // logout
        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}