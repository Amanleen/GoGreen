package model;

public class GreenEntryNew {

    private int postId;
    private int postedByUserId; // or user object
    private String postType;
    private String postMessage;
    private String datePosted;
    private String postImageURL;
    private int numOfShares;
    private int numOfStars;


    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public int getPostedByUserId() {
        return postedByUserId;
    }
    public void setPostedByUserId(int postedByUserId) {
        this.postedByUserId = postedByUserId;
    }
    public String getPostType() {
        return postType;
    }
    public void setPostType(String postType) {
        this.postType = postType;
    }
    public String getPostMessage() {
        return postMessage;
    }
    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }
    public String getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
    public String getPostImageURL() {
        return postImageURL;
    }
    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }
    public int getNumOfShares() {
        return numOfShares;
    }
    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }
    public int getNumOfStars() {
        return numOfStars;
    }
    public void setNumOfStars(int numOfStars) {
        this.numOfStars = numOfStars;
    }
}

