package com.na.celebrities.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.na.celebrities.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Noha on 11/11/2017.
 */

public class DisplayFullSizeImageActivity extends Activity {
    private static final String TAG = DisplayFullSizeImageActivity.class.getName();
    ImageView ivFullSizeImage;
    ImageButton ibDownloadImage;
    Intent intent;
    String imagePath, personName;
    int imageWidth, imageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_image);

        initializeVars();

        ibDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ask for permission
                if (isStoragePermissionGranted())
                    downloadImage();
            }
        });

    }

    public void initializeVars() {

        intent = getIntent();
        ivFullSizeImage = (ImageView) findViewById(R.id.ivFullSizeImage);
        ibDownloadImage = (ImageButton) findViewById(R.id.ibDownloadImage);
        personName = intent.getStringExtra("personName");
        imagePath = intent.getStringExtra("imagePath");
        imageWidth = intent.getIntExtra("imageWidth", 400);
        imageHeight = intent.getIntExtra("imageHeight", 600);
        Log.d(TAG, "imageWidth = " + imageWidth);
        Log.d(TAG, "imageHeight = " + imageHeight);

        //Set Image To ImageView
        Picasso.with(DisplayFullSizeImageActivity.this).load(imagePath).into(ivFullSizeImage);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageWidth, imageHeight);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        ivFullSizeImage.setLayoutParams(layoutParams);

    }

    /**
     * download image and save it to device
     */
    public void downloadImage() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String timeStamp = tsLong.toString();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        // Create directory
        if (!storageDir.exists()) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            // have the object build the directory structure, if needed.
            storageDir.mkdirs();
            Log.d(TAG, "directory created");
        }
        final ImageView profile = new ImageView(DisplayFullSizeImageActivity.this);
        final String img_path = personName + "_" + timeStamp + ".jpg";
        Log.d(TAG, "image name " + img_path);
        final File finalStorageDir = storageDir;
        Picasso.with(DisplayFullSizeImageActivity.this).load(imagePath).into(profile, new Callback() {
            @Override
            public void onSuccess() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Save bitmap to local
                        Bitmap bitmap = ((BitmapDrawable) profile.getDrawable()).getBitmap();
                        File file = new File(finalStorageDir, img_path);
                        try {
                            if (!file.exists()) {
                                FileOutputStream ostream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                                ostream.close();
                                Log.d(TAG, "image saved");
                                Toast.makeText(DisplayFullSizeImageActivity.this, "Photo Saved in Pictures", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 100);
            }

            @Override
            public void onError() {
            }
        });
    }

    //permission region

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Toast.makeText(this, "Permission Denied, cannot save to drive ", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            downloadImage();
        } else
            Toast.makeText(this, "Permission Denied, cannot use local drive ", Toast.LENGTH_SHORT).show();
    }

    //end region
}
