package com.grapesoftware.paycal_resist;

public class City {

    public String name;
    public int id;
    public long latitude;
    public long longitude;


    public City() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public City(String name, int id,long latitude, long longitude) {
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}


