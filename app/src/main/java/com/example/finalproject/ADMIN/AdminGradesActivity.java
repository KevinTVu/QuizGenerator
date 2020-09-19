package com.example.finalproject.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;
import com.example.finalproject.STUDENT.GradesActivity;
import com.example.finalproject.STUDENT.Quiz;
import com.example.finalproject.STUDENT.QuizAnswersUtil;
import com.example.finalproject.STUDENT.QuizUtil;
import com.example.finalproject.Student;

import java.util.ArrayList;
import java.util.Date;

/**
 * AdminGradesActivity - this activity will be responsible for displaying the students completed quiz grade. The admin will be allowed to choose a
 * student from a drop down menu and the student's grade for completed quizzes will be displayed in a RecyclerView
 */
public class AdminGradesActivity extends AppCompatActivity {
    private Button backButton;
    private RecyclerView quizzesRecyclerView;
    private ArrayList<Username> listOfStudents;
    private DatabaseHelper db;
    private Spinner studentSpinner;
    private ArrayList<String> studentNames;
    private ArrayAdapter<String> adapter;
    private ArrayList<QuizUtil> quizzes;
    private ArrayList<String> grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_grades);

        backButton = findViewById(R.id.adminPreviousButton);
        quizzesRecyclerView = findViewById(R.id.studentQuizzesRecyclerView);
        studentSpinner = findViewById(R.id.studentSpinner);
        studentNames = new ArrayList<String>();
        db = new DatabaseHelper(this);

        //Grabs the all the users in the database and stores it into an ArrayList of usernames
        listOfStudents = db.listContacts();

        //For loop that will loop until it reaches the size of the arraylist.
        for(int index = 0; index < listOfStudents.size(); ++index)
        {
            //The username of each student will be added to an arraylist of strings
            studentNames.add(listOfStudents.get(index).getUsername());
        }

        //Uses the adapter to populate the drop down menu with the student's username
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.my_custom_dropdown, studentNames);

        //Sets the adapter
        studentSpinner.setAdapter(adapter);

        //Listener that will listen to see if the admin chooses a username from the drop down menu
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quizzes = db.listCompletedQuiz(studentNames.get(position));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                quizzesRecyclerView.setLayoutManager(linearLayoutManager);

                StudentRecyclerviewAdapter adapter = new StudentRecyclerviewAdapter(getApplicationContext(), quizzes, position);
                quizzesRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * This RecyclerViewAdapter will be responsible for populating the RecyclerView with the grades of the specific student
     */
    public class StudentRecyclerviewAdapter extends RecyclerView.Adapter <StudentRecyclerviewAdapter.ViewHolder> {

        private final ArrayList<QuizUtil> myQuiz;
        private int position;
        Context context;


        /**
         * Default constructor that will have context parameter, the arraylist of QuizUtil which contains quiz information, and the position
         * @param context
         * @param users
         * @param position
         */
        public StudentRecyclerviewAdapter(Context context, ArrayList<QuizUtil> users, int position) {

            this.context = context;
            this.myQuiz = users;
            this.position = position;


        }

        @NonNull
        @Override
        /**
         * onCreateViewHolder function that will inflate the layout
         */
        public StudentRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_quiz_list, parent, false);
            return new StudentRecyclerviewAdapter.ViewHolder(view);
        }

        @Override
        /**
         * onBindViewHolder method that will be populating the RecyclerView.
         */
        public void onBindViewHolder(final StudentRecyclerviewAdapter.ViewHolder holder, int position) {
            final QuizUtil mQ = myQuiz.get(position);
            ArrayList<String> grades;

            grades = db.getGrades(QuizAnswersUtil.list.get(position), myQuiz.get(position).getUserName());
            // set the test number
            holder.viewID.setText(String.valueOf(position + 1));

            // set the name of the exam to content
            holder.contentView.setText(mQ.getTopic() +  " - " + grades.get(1) + "% (" + grades.get(2) + ")"  );  // set the topic

            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return myQuiz.size();
        }

        // an extends View holder
        class ViewHolder extends  RecyclerView.ViewHolder {

            final View view;
            final TextView viewID;      // TextView that hold the id
            final TextView contentView; // TextView that hold the content

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
                viewID = view.findViewById(R.id.id);
                contentView = view.findViewById(R.id.contents);
            }
        }



    }

}