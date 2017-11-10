package com.na.celebrities.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.na.celebrities.R;
import com.na.celebrities.adapter.CelebritiesListAdapter;
import com.na.celebrities.model.Celebrities;
import com.na.celebrities.util.ConfigSettings;
import com.na.celebrities.util.EndlessScrollListener;
import com.na.celebrities.util.VollySingletone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    ListView lvCelebrities;
    static ArrayList<Celebrities> celebritiesArrayList;
    CelebritiesListAdapter celebritiesListAdapter;
    int pageNumber;
    static int totalPagesNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageNumber = 1;
        lvCelebrities = (ListView) findViewById(R.id.lvCelebrities);
        getCelebritiesList(pageNumber);
        lvCelebrities.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                //increase page number to load next page
                int nextPage = ++pageNumber;
                if (nextPage <= totalPagesNumber) {
                    loadMore(nextPage);
                    // ONLY if more data is actually being loaded; false otherwise.
                    return true;
                } else return false;
            }
        });


    }


    // region JSON Parsing

    /**
     * parse JSON Object to get celebrities array list
     *
     * @param jsonStr
     * @return
     */
    public static ArrayList<Celebrities> parseJson(String jsonStr) {
        ArrayList<Celebrities> celebritiesArray = new ArrayList<Celebrities>();
        JSONArray jsonArray = null;

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr.toString());
                totalPagesNumber = jsonObj.getInt(ConfigSettings.TOTAL_PAGES);
                Log.d(TAG, "current page is " + jsonObj.getString(ConfigSettings.PAGE));
                jsonArray = jsonObj.getJSONArray(ConfigSettings.RESULTS);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "Celebrities list catch JSONException ");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject celebrityJsonObj = jsonArray.getJSONObject(i);
                    Celebrities celebritiesObject =
                            new Celebrities(celebrityJsonObj.getInt(ConfigSettings.ID)
                                    , celebrityJsonObj.getString(ConfigSettings.NAME));
                    celebritiesArray.add(celebritiesObject);
                    Log.d(TAG, "celebrity added");

                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.d(TAG, "celebrity catch JSONException");
                }
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        return celebritiesArray;
    }

    // End Region

    //region API get celebrities list

    /**
     * get celebrities list for the first time
     *
     * @param pageNumber
     */
    protected void getCelebritiesList(int pageNumber) {
        RequestQueue queue = VollySingletone.getInstance(this).getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.GET, ConfigSettings.CELEBRITIES_URL + pageNumber
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display response string.
                Log.d(TAG, response.toString());
                celebritiesArrayList = parseJson(response);
                Log.d(TAG, "Celebrities list size = " + celebritiesArrayList.size());
                celebritiesListAdapter = new CelebritiesListAdapter(MainActivity.this, celebritiesArrayList);
                lvCelebrities.setAdapter(celebritiesListAdapter);
                celebritiesListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed : No HTTP setting", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed : No HTTP setting ");
            }
        });
        queue.add(myReq);
    }

    /**
     * * get celebrities list when scrolling
     *
     * @param pageNumber
     */
    protected void loadMore(int pageNumber) {
        RequestQueue queue = VollySingletone.getInstance(this).getRequestQueue();
        StringRequest myReq = new StringRequest(Request.Method.GET, ConfigSettings.CELEBRITIES_URL + pageNumber
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display response string.
                Log.d(TAG, response.toString());
                celebritiesArrayList.addAll(parseJson(response));
                Log.d(TAG, "load more Celebrities list size = " + celebritiesArrayList.size());
                celebritiesListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed : No HTTP setting", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed : No HTTP setting ");
            }
        });
        queue.add(myReq);

    }

    //End Region
}

