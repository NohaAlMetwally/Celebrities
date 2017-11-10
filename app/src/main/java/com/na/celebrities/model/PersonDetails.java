package com.na.celebrities.model;

/**
 * Created by Noha on 11/10/2017.
 */

public class PersonDetails {

    String personName, personBiography, personBirthday, personDeathDay, personPlaceOfBirth;
    int personId, personGender;

    public PersonDetails() {
    }

    public PersonDetails(int personId, String personName, String personBiography, String personBirthday
            , String personDeathDay, String personPlaceOfBirth, int personGender) {
        this.personName = personName;
        this.personBiography = personBiography;
        this.personBirthday = personBirthday;
        this.personDeathDay = personDeathDay;
        this.personPlaceOfBirth = personPlaceOfBirth;
        this.personGender = personGender;
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonBiography() {
        return personBiography;
    }

    public void setPersonBiography(String personBiography) {
        this.personBiography = personBiography;
    }

    public String getPersonBirthday() {
        return personBirthday;
    }

    public void setPersonBirthday(String personBirthday) {
        this.personBirthday = personBirthday;
    }

    public String getPersonDeathDay() {
        return personDeathDay;
    }

    public void setPersonDeathDay(String personDeathDay) {
        this.personDeathDay = personDeathDay;
    }

    public String getPersonPlaceOfBirth() {
        return personPlaceOfBirth;
    }

    public void setPersonPlaceOfBirth(String personPlaceOfBirth) {
        this.personPlaceOfBirth = personPlaceOfBirth;
    }

    public int getPersonGender() {
        return personGender;
    }

    public void setPersonGender(int personGender) {
        this.personGender = personGender;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


}
