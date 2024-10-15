package com.example.assignmentdn;

public class Post {

    private int eID;
    private String eTitle,eDescription;
    private  byte[] eImage;


    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public String geteTitle() {
        return eTitle;
    }

    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }

    public String geteDescription() {
        return eDescription;
    }

    public void seteDescription(String eDescription) {
        this.eDescription = eDescription;
    }

    public byte[] geteImage() {
        return eImage;
    }

    public void seteImage(byte[] eImage) {
        this.eImage = eImage;
    }
}
