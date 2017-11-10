package com.na.celebrities.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.na.celebrities.R;
import com.na.celebrities.activity.PersonDetailsActivity;
import com.na.celebrities.model.Celebrities;

import java.util.ArrayList;

/**
 * Created by Noha on 11/10/2017.
 */

public class CelebritiesListAdapter extends BaseAdapter {
    private static final String TAG = CelebritiesListAdapter.class.getName();
    private ArrayList<Celebrities> celebritiesArrayList;
    private Context context;
    LayoutInflater inflater;
    Intent iStartDetailsActivity;

    public CelebritiesListAdapter(Context context, ArrayList<Celebrities> celebritiesArrayList) {
        this.context = context;
        this.celebritiesArrayList = celebritiesArrayList;
    }


    @Override
    public int getCount() {
        return celebritiesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_celebrity, null);
            Holder holder = new Holder();
            holder.tvCelebrity = (TextView) view.findViewById(R.id.tvCelebrity);
            holder.llCelebrity = (LinearLayout) view.findViewById(R.id.llCelebrity);
            view.setTag(holder);
        }
        final Holder holder = (Holder) view.getTag();
        holder.tvCelebrity.setText(celebritiesArrayList.get(i).getCelebrityName());
        holder.llCelebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStartDetailsActivity = new Intent(context, PersonDetailsActivity.class);
                iStartDetailsActivity.putExtra("personId", celebritiesArrayList.get(i).getCelebrityId());
                iStartDetailsActivity.putExtra("personName", celebritiesArrayList.get(i).getCelebrityName());
                context.startActivity(iStartDetailsActivity);
            }
        });
        return view;
    }

    class Holder {
        LinearLayout llCelebrity;
        TextView tvCelebrity;
    }
}
