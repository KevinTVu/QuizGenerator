package com.example.finalproject.STUDENT;
/**
 * A grade review activity that shows the topic that the user too,
 * the percentage they received, and the letter grade
 */

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {

    // Global variables
    private DatabaseHelper db;                  // declare database
    private ArrayList<String> gradeInformation; // Array list of grade information
    private TextView topicTextView;
    private TextView percentageTextView;
    private TextView gradeTextView;
    private Button gradeReportPreviousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        // set all textviews and button to each key from xml file
        topicTextView = findViewById(R.id.subjectTextView);
        percentageTextView = findViewById(R.id.gradePercentageTextView);
        gradeTextView = findViewById(R.id.letterGradeTextView);
        gradeReportPreviousButton = findViewById(R.id.gradeReportPrevious);

        // set the database from the system, contained all the data
        db = new DatabaseHelper(this);

        // back up to previous page, if this button is click
        gradeReportPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // get the key quizID to get the grade information
        Intent intent = getIntent();
        int quizID = intent.getIntExtra("quizID", 0);
        gradeInformation = db.getGrades(quizID, QuizAnswersUtil.username);

        //Format the percentage
        double grade = Double.parseDouble(gradeInformation.get(1));
        @SuppressLint("DefaultLocale") String strDouble = String.format("%.1f", grade);

        // populate the data into the text views
        topicTextView.setText("Topic: " + gradeInformation.get(0));
        percentageTextView.setText("Percentage: " + strDouble + " %");
        gradeTextView.setText("Grade: " + gradeInformation.get(2));
    }
}