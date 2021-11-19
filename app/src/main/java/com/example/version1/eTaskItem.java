package com.example.version1;

public class eTaskItem {
    private int mImageResource;
    private int mImageResource2;
    private String mText1;
    private String mText2;

    public eTaskItem(int imageResource, int imageResource2, String text1, String text2){
        mImageResource = imageResource;
        mImageResource2 = imageResource2;
        mText1 = text1;
        mText2 = text2;

    }

    public void changeText1(String text){
        mText1 = text;
    }

    public int getmImageResource(){
        return mImageResource;
    }
    public int getmImageResource2(){
        return mImageResource2;
    }
    public String getText1(){
        return mText1;
    }
    public String getText2(){
        return mText2;
    }
}

