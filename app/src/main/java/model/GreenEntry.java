package model;

/**
 * Created by amanleenpuri on 4/3/16.
 */
public class GreenEntry {

    public String userName;
    public int userId;
    public String text;
    public String type;
    public String dateTime;
    public int numberOfStars;

    public GreenEntry(){
    }


    public GreenEntry(String type, int userId, String text){
        this.type = type;
        this.userId = userId;
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }

    String imageUrl;

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }



}
