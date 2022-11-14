package com.example.metflix;

public class SliderItem {

    // Here you can use String variable to store url
    // If you want to load image from the internet
    private  int image;

    SliderItem(int image){
        this.image= image;
    }
    public int getImage() {
        return image;
    }
}
