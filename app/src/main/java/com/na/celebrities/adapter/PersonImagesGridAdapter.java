package com.na.celebrities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.na.celebrities.R;
import com.na.celebrities.model.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Noha on 11/10/2017.
 */

public class PersonImagesGridAdapter extends BaseAdapter {
    private static final String TAG = PersonImagesGridAdapter.class.getName();
    Context context;
    LayoutInflater inflater;
    private ImageLoader mImageLoader;
    ArrayList<Images> personImagesArrayList;

    public PersonImagesGridAdapter(Context context, ArrayList<Images> personImagesArrayList) {
        this.context = context;
        this.personImagesArrayList = personImagesArrayList;
    }

    @Override
    public int getCount() {
        return personImagesArrayList.size();
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_person_image, null);
            Holder holder = new Holder();
            holder.ivPersonImage = (ImageView) view.findViewById(R.id.ivPersonImage);
            view.setTag(holder);
        }
        Holder holder = (Holder) view.getTag();
        Images imageObject = personImagesArrayList.get(i);
        Picasso.with(context).load(imageObject.getImageFilePath()).into(holder.ivPersonImage);
        return view;
    }

    class Holder {
        ImageView ivPersonImage;
    }
}
