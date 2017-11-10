package com.na.celebrities.model;

/**
 * Created by Noha on 11/10/2017.
 */

public class Celebrities {


    private String celebrityName;
    private int celebrityId;

    public Celebrities() {
    }

    public Celebrities(int id, String celebrityName) {
        this.celebrityId = id;
        this.celebrityName = celebrityName;
    }

    public String getCelebrityName() {
        return celebrityName;
    }

    public void setCelebrityName(String celebrityName) {
        this.celebrityName = celebrityName;
    }

    public int getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(int celebrityId) {
        this.celebrityId = celebrityId;
    }


}
