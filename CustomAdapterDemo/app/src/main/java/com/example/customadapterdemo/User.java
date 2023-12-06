package com.example.customadapterdemo;

public class User {
    public String name, phoneNumber;
    public Sex sex;

    public User(String name, String phoneNumber, Sex sex) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Sex getSex() {
        return sex;
    }
}
