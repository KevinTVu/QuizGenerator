package com.example.finalproject.ADMIN;

/**
 * Quizzes class that will storing the id, amount of question, topic, and time limit
 */
public class Quizzes {
    int id, amountOfQuestion, timeLimit;
    String topic;

    //Default constructor that will not be used
    public Quizzes() {}

    //Parameterized constructor that will initialize the id, amount of question, time limit and topic
    public Quizzes(int id, String topic, int amountOfQuestion, int timeLimit)
    {
        this.id = id;
        this.amountOfQuestion = amountOfQuestion;
        this.timeLimit = timeLimit;
        this.topic = topic;
    }

    //Getter function that will return the ID of the quiz
    public int getId()
    {
        return id;
    }

    //Getter function that will return amountOfQuestions of the  quiz
    public int getAmountOfQuestion()
    {
        return amountOfQuestion;
    }

    //Getter function that will return time limit of the quiz
    public int getTimeLimit()
    {
        return timeLimit;
    }

    //Getter function that will return the topic of the quiz
    public String getTopic()
    {
        return topic;
    }



}
