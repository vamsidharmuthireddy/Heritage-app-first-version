package com.example.harshil.anew;

/**
 * Created by Harshil on 7/2/2016.
 */
public class Locn {
    /**
    *This class sets and gets latitude and longitude of the location
    *
    */
    public double longtd;
    public double latd;

    public void setlatd(double text)
    {
        this.latd=text;
    }
    public void setLongtd(double text)
    {
        this.longtd=text;
    }

    public double getLatd() {
        return latd;
    }

    public double getLongtd() {
        return longtd;
    }

}
