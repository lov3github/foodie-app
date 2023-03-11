package com.example.myapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    private String fullName;
    private String phoneNumber;
    private String password;
    private String email;
    private String dateOfBirth;
    private String gender;


    public User() {
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String fullName, String phoneNumber, String password, String email, String dateOfBirth, String gender) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + '\n' +
                "fullName='" + fullName + '\'' + '\n' +
                ", phoneNumber='" + phoneNumber + '\'' + '\n' +
                ", password='" + password + '\'' + '\n' +
                ", email='" + email + '\'' + '\n' +
                ", dateOfBirth='" + dateOfBirth + '\'' + '\n' +
                ", gender='" + gender + '\'' + '\n' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullName", fullName);
        result.put("phoneNumber", phoneNumber);
        result.put("password", password);
        result.put("email", email);
        result.put("dateOfBirth", dateOfBirth);
        result.put("gender", gender);
        return result;
    }


}
