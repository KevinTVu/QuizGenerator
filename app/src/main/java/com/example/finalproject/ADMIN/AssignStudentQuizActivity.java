package com.example.finalproject.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.DatabaseHelper;
import com.example.finalproject.R;
import com.example.finalproject.ADMIN.Username;
import com.example.finalproject.Student;

import java.util.ArrayList;

public class AssignStudentQuizActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView studentRecyclerView;
    private ArrayList<Username> listOfStudents;

    private Button previousBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_student);

        db = new DatabaseHelper(this);
        studentRecyclerView = findViewById(R.id.assignStudentRecyclerView);
        previousBtn = findViewById(R.id.previous);
        listOfStudents = db.listContacts();

        //Sets the studentRecyclerView to the linearLayoutmManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(linearLayoutManager);

        //Creates a new RecyclerViewAdapter object and sends the current context and the users that are registered in the system
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this, listOfStudents);
        studentRecyclerView.setAdapter(adapter);

        //Previous button listener, if clicked, it will move to previous activity
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * This RecyclerViewAdapter will be responsible for allowing the admin to choose which student they want to assign a quiz to
     */
    public class RecyclerviewAdapter extends RecyclerView.Adapter <RecyclerviewAdapter.ViewHolder> {

        private final ArrayList<Username> usernames;
        Context context;


        /**
         * Default constructor that will initialized the context and the arraylist of usernames
         * @param context
         * @param users
         */
        public RecyclerviewAdapter(Context context, ArrayList<Username> users) {
            this.usernames = users;
            this.context = context;
        }

        @NonNull
        @Override
        /**
         * onCreateViewHolder that will inflate the layout
         */
        public RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.test_history, parent, false);
            return new RecyclerviewAdapter.ViewHolder(view);
        }

        @Override
        /**
         * onBindViewholder is responsible for sending the username to the assignQuizActivity based on the admin selection
         */
        public void onBindViewHolder(final RecyclerviewAdapter.ViewHolder holder, int position) {
            final Username username = usernames.get(position);

            // set the test number
            holder.viewID.setText(String.valueOf(position + 1));

            // set the name of the exam to content
            holder.contentView.setText(username.getUsername());

            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    // set the next window class to be perform
                    Intent intent = new Intent(context, AssignQuizActivity.class);
                    intent.putExtra("username", username.getUsername());
                    context.startActivities(new Intent[]{intent});
                }
            });
        }

        @Override
        /**
         * getItemCount function that will return the size of the arraylist
         */
        public int getItemCount() {
            return usernames.size();
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






