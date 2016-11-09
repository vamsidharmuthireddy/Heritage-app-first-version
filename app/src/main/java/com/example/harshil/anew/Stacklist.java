package com.example.harshil.anew;

/**
 * Created by Harshil on 6/22/2016.
 */
public class Stacklist extends Locn{
/**
*Sets and gets the interestpoint information from the xml file
*
*/
    
    private int id;
    private String title;
    private Locn loctn;
    private String captn;
    private String about;
    private String imgloc;
    private double distance;

    public void setDistance(double distance)
    {
        this.distance=distance;
    }

    public double getDistance()
    {
        return distance;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }
    public void setCaptn(String captn) {
        this.captn = captn;
    }
    public String getCaptn()
    {
        return captn;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public void setImgUrl(String imgloc) {
        this.imgloc = imgloc;
    }

    public String getImgUrl() {
        return imgloc;
    }
}
