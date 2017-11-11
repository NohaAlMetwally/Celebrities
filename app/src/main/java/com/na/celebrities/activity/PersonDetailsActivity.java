package com.na.celebrities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.na.celebrities.R;
import com.na.celebrities.adapter.PersonImagesGridAdapter;
import com.na.celebrities.model.Images;
import com.na.celebrities.model.PersonDetails;
import com.na.celebrities.util.ConfigSettings;
import com.na.celebrities.util.MyGridView;
import com.na.celebrities.util.VollySingletone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Noha on 11/10/2017.
 */

public class PersonDetailsActivity extends AppCompatActivity {
    private static final String TAG = PersonDetailsActivity.class.getName();
    Intent intent;
    String personName;
    int personId;
    PersonDetails personDetailsObject;
    TextView tvBioDetails, tvGenderDetails, tvBirthDayDetails, tvDeathdayDetails, tvPlaceOfBirthDetails;
    LinearLayout llDeathDay;
    MyGridView gvPersonImages;
    PersonImagesGridAdapter personImageGridAdapter;
    ArrayList<Images> ImagesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        intent = getIntent();
        personName = intent.getStringExtra("personName");
        personId = intent.getIntExtra("personId", 0);
        PersonDetailsActivity.this.setTitle(personName);
        initializeVars();
        getPersonDetails();
        getImagesArray();
    }

    private void initializeVars() {
        tvBioDetails = (TextView) findViewById(R.id.tvBioDetails);
        tvGenderDetails = (TextView) findViewById(R.id.tvGenderDetails);
        tvBirthDayDetails = (TextView) findViewById(R.id.tvBirthDayDetails);
        tvDeathdayDetails = (TextView) findViewById(R.id.tvDeathdayDetails);
        tvPlaceOfBirthDetails = (TextView) findViewById(R.id.tvPlaceOfBirthDetails);
        llDeathDay = (LinearLayout) findViewById(R.id.llDeathday);
        gvPersonImages = (MyGridView) findViewById(R.id.gvPersonImages);

    }

    //region Json Parsing

    /**
     * parse person details json object
     *
     * @param jsonStr
     * @return
     */
    public static PersonDetails parseJsonPersonDetails(String jsonStr) {
        PersonDetails personDetails = new PersonDetails();
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr.toString());
                personDetails = new PersonDetails(jsonObj.getInt(ConfigSettings.ID), jsonObj.getString(ConfigSettings.NAME)
                        , jsonObj.getString(ConfigSettings.BIOGRAPHY), jsonObj.getString(ConfigSettings.BIRTHDAY),
                        jsonObj.getString(ConfigSettings.DEATHDAY), jsonObj.getString(ConfigSettings.PLACE_OF_BIRTH),
                        jsonObj.getInt(ConfigSettings.GENDER));
                Log.d(TAG, jsonObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "person details catch JSONException ");
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        return personDetails;
    }

    /**
     * parse image json object
     * @param jsonStr
     * @return
     */
    public static ArrayList<Images> parseJsonImage(String jsonStr) {
        ArrayList<Images> personImagesArrayList = new ArrayList<Images>();
        JSONArray jsonArray = null;

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr.toString());

                jsonArray = jsonObj.getJSONArray(ConfigSettings.PROFILES);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "Images catch JSONException ");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject imageJsonObj = jsonArray.getJSONObject(i);
                    Images imageObject =
                            new Images(ConfigSettings.IMAGE_PATH_PRE_URL
                                    + imageJsonObj.getString(ConfigSettings.FILE_PATH)
                                    , imageJsonObj.getInt(ConfigSettings.HEIGHT)
                                    , imageJsonObj.getInt(ConfigSettings.WIDTH)
                                    , imageJsonObj.getInt(ConfigSettings.ASPECT_RATIO));
                    personImagesArrayList.add(imageObject);
                    Log.d(TAG, "Image added");

                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.d(TAG, "Image catch JSONException");
                }
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        return personImagesArrayList;
    }

    // End Region

    // region API

    /**
     * get person details
     */
    protected void getPersonDetails() {
        RequestQueue queue = VollySingletone.getInstance(this).getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.GET, ConfigSettings.PERSON_DETAILS_URL + personId
                + ConfigSettings.API_KEY_AND_LANGUAGE
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display response string.
                Log.d(TAG, response.toString());
                personDetailsObject = parseJsonPersonDetails(response);
                setTextViews();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonDetailsActivity.this, "Failed : No HTTP setting", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed : No HTTP setting ");
            }
        });
        queue.add(myReq);
    }

    /**
     * get person images array
     */
    protected void getImagesArray() {
        RequestQueue queue = VollySingletone.getInstance(this).getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.GET, ConfigSettings.PERSON_DETAILS_URL + personId
                + ConfigSettings.IMAGES + ConfigSettings.API_KEY_AND_LANGUAGE
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display response string.
                Log.d(TAG, response.toString());
                ImagesArrayList = parseJsonImage(response);
                Log.d(TAG, "Images list size = " + ImagesArrayList.size());
                personImageGridAdapter = new PersonImagesGridAdapter(PersonDetailsActivity.this, ImagesArrayList);
                gvPersonImages.setAdapter(personImageGridAdapter);
                personImageGridAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonDetailsActivity.this, "Failed : No HTTP setting", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed : No HTTP setting ");
            }
        });
        queue.add(myReq);
    }

    // end region

    /**
     * set person details to textView
     */
    public void setTextViews() {

        String Gender;
        String deathDay;

        tvBioDetails.setText(personDetailsObject.getPersonBiography());

        if (personDetailsObject.getPersonGender() == 2)
            Gender = "Male";
        else Gender = "Female";
        tvGenderDetails.setText(Gender);

        tvBirthDayDetails.setText(personDetailsObject.getPersonBirthday());

        deathDay = personDetailsObject.getPersonDeathDay();
        if (deathDay != null && !deathDay.isEmpty() && !deathDay.equals("null")) {
            llDeathDay.setVisibility(View.VISIBLE);
            tvDeathdayDetails.setText(personDetailsObject.getPersonDeathDay());
        }

        tvPlaceOfBirthDetails.setText(personDetailsObject.getPersonPlaceOfBirth());

    }
}
