package com.example.abhishek.e_farmer;

/**
 * Created by abhishek on 16/03/18.
 */

public class User {
    public String firstName;
    public String lastName;
    public String emailId;
    public String phoneNumber;
    public String password;
    public String Address;

    public User(String firstName,String lastName,String emailId,String phoneNumber,String password, String Address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.Address = Address;
    }

    public User(){

    }

}
