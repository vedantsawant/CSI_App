package com.example.csiapp1;

import com.google.firebase.database.Exclude;

public class Upload {

    private String mworkshopName;
    private String mimageUrl;
    private String mKey;
    private String mWorkshopDetails;
    private String mDate;

    public Upload(){
        //empty constructor needed
    }

    public String getmWorkshopDetails() {
        return mWorkshopDetails;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setmWorkshopDetails(String mWorkshopDetails) {
        this.mWorkshopDetails = mWorkshopDetails;
    }

    public Upload(String workshopName, String workshopDetails, String imageUrl, String date){
        mimageUrl = imageUrl;
        mworkshopName =  workshopName;
        mWorkshopDetails = workshopDetails;
        mDate = date;

    }

    public Upload(String workshopName, String workshopDetails, String imageUrl){
        if(workshopName.trim().equals("")){
             workshopName = "No Name";
        }
        if(workshopDetails.trim().equals("")){
            workshopDetails = "No Details available";
        }

        mimageUrl = imageUrl;
        mworkshopName =  workshopName;
        mWorkshopDetails = workshopDetails;
    }

    public String getMworkshopName() {
        return mworkshopName;
    }

    public void setMworkshopName(String mworkshopName) {
        this.mworkshopName = mworkshopName;
    }

    public String getMimageUrl() {
        return mimageUrl;
    }

    public void setMimageUrl(String mimageUrl) {
        this.mimageUrl = mimageUrl;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
