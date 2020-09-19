package com.example.finalproject.ADMIN;

/**
 * Username class that will be storing the username, password, email, and phone number of the student
 */
public class Username {
    String username, password, email, phoneNumber;

    //Default constructor that will not be used
    public Username(){}

    //Parameterized constructor that will initialize the username, password, email, and phonenumber
    public Username(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //Getter function that will return username of the student
    public String getUsername() {
        return username;
    }

    //Getter function that will return the password of the student
    public String getPassword() {
        return password;
    }

    //Getter function that will return email of the student
    public String getEmail() {
        return email;
    }

    //Getter function that will return phone number of the student
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
