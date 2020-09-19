package com.example.finalproject.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;
import com.example.finalproject.ADMIN.Username;

import java.util.ArrayList;

public class AssignQuizActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView quizRecyclerView;
    private String username;
    private ArrayList<Quizzes> listOfQuizzes;
    private Button previous3, backToAdminMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_quiz);

        db = new DatabaseHelper(this);
        quizRecyclerView = findViewById(R.id.assignQuizRecyclerView);
        username = getIntent().getStringExtra("username");

        //Stores all thw quizzes into an ArrayList of quizzes which contains the question, answer choices, and correct answer
        listOfQuizzes = db.listQuizInformation();

        //Previous button that will allow the user to move to the previous activity
        previous3 = findViewById(R.id.previous3);

        //allows the user to move back to the admin menu
        backToAdminMenu = findViewById(R.id.back_to_admin_menu);

        //Sets the quizRecyclerView to the linearLayoutmManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        quizRecyclerView.setLayoutManager(linearLayoutManager);

        //Creates a new RecyclerViewAdapter object and sends the current context, username of the student and the list of quiz questions
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, username, listOfQuizzes);
        quizRecyclerView.setAdapter(adapter);

        //Previous button listener, if clicked, it will move to previous activity
        previous3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Back to menu listener, if clicked, will move to admin activity
        backToAdminMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToAdmin = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(moveToAdmin);
            }
        });


    }

    /**
     * This RecyclerViewAdapter will be responsible for assigning a specific quiz to a user
     */
    public  class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {

        private final ArrayList<Quizzes> listOfQuizzes;
        private String username;
        DatabaseHelper db;
        Context context;

        /**
         * Default constructor that will initialize the username, listofQuizzes, and context
         * @param context
         * @param username
         * @param listOfQuizzes
         */
        public RecyclerViewAdapter(Context context, String username, ArrayList<Quizzes> listOfQuizzes) {
            this.username = username;
            this.listOfQuizzes = listOfQuizzes;
            this.context = context;
        }

        @NonNull
        @Override
        /**
         * onCreateViewHolder that will inflate the layout
         */
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.test_history, parent, false);
            return new RecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        /**
         * onBindViewHolder that grabs assigns the specific quiz based off the users click position. It will display that the quiz is assigned by using a toast message
         */
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
            final Quizzes quiz = listOfQuizzes.get(position);

            // set the test number
            holder.viewID.setText(String.valueOf(position + 1));

            // set the name of the exam to content
            holder.contentView.setText(quiz.getTopic());

            //listener that will be used to assign a quiz
            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    db = new DatabaseHelper(context);
                    db.insertAssignQuiz(username, quiz.getId(), quiz.getTopic());
                    Toast.makeText(context, username + " was assigned the following quiz: " + quiz.getTopic(), Toast.LENGTH_LONG).show();

                }
            });
        }

        @Override
        /**
         * Returns the count of the amount of quizzes in the arraylist
         */
        public int getItemCount() {
            return listOfQuizzes.size();
        }

        // an extends View holder
        class ViewHolder extends RecyclerView.ViewHolder {

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