package com.na.celebrities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.na.celebrities.R;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_celebrity, null);
            Holder holder = new Holder();
            holder.tvCelebrity = (TextView) view.findViewById(R.id.tvCelebrity);
            view.setTag(holder);
        }
        final Holder holder = (Holder) view.getTag();
        holder.tvCelebrity.setText(celebritiesArrayList.get(i).getCelebrityName());
        return view;
    }

    class Holder {
        TextView tvCelebrity;
    }
}
