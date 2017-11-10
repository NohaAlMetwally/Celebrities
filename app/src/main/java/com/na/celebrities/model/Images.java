package com.na.celebrities.model;

/**
 * Created by Noha on 11/10/2017.
 */

public class Images {
    String imageFilePath;
    int imageHeight, imageWidth, imageAspectRatio;

    public Images() {
    }

    public Images(String imageFilePath
            , int imageHeight, int imageWidth, int imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageFilePath = imageFilePath;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageAspectRatio() {
        return imageAspectRatio;
    }

    public void setImageAspectRatio(int imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
    }


}
