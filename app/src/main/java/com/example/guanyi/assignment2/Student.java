package com.example.guanyi.assignment2;

/**
 * Created by Guanyi on 3/23/2015.
 */
public class Student {

    private int student_number;
    private String first_name;
    private String last_name;
    private String email_address;

    public void setId(int ID) {
        student_number = ID;
    }
    public void setFirstName(String firstName) {
        first_name = firstName;
    }
    public void setLastName(String lastName) {
        last_name = lastName;
    }
    public void setEmail(String email) { email_address = email; }
    public int getId() {
        return student_number;
    }
    public String getFirstName() {
        return first_name;
    }
    public String getLastName() {
        return last_name;
    }
    public String getEmail() {
        return email_address;
    }
}
