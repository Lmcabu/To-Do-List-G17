package com.example.version1;

public class eTaskItem {
    private int mImageResource;
    private int mImageResource2;
    private String mid;
    private String mlistId;
    private String mdetails;
    private String mtitle;

    public eTaskItem(int imageResource, int imageResource2, String id, String listId, String details, String title){
        mImageResource = imageResource;
        mImageResource2 = imageResource2;
        mid = id;
        mlistId = listId;
        mdetails = details;
        mtitle = title;

    }

    public int getmImageResource() {
        return mImageResource;
    }

    public int getmImageResource2() {
        return mImageResource2;
    }

    public String getMid() {
        return mid;
    }

    public String getMlistId() {
        return mlistId;
    }

    public String getMdetails() {
        return mdetails;
    }

    public String getMtitle() {
        return mtitle;
    }
}

