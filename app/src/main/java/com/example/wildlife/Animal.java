package com.example.wildlife;

public class Animal {
    public Animal(String animal, String latitude, String longitude, String time) {
        Animal = animal;
        Latitude = latitude;
        Longitude = longitude;
        Time = time;
    }

    public String Animal;
    public String Latitude;
    public String Longitude;
    public String Time;

    public Animal() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


}
