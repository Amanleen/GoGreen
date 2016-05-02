package model;

import java.io.Serializable;

public class User implements Serializable{
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String roleType;
//    private int roleId;
    private String interestArea;
    private String city;
    private String state;
    private int followersNum; // Should be array of users or user id
    private int followingNum; // Should be array of users or user id
    private String imageURL;


    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
//    public int getRoleId() {
//        return roleId;
//    }
//    public void setRoleId(int roleId) {
//        this.roleId = roleId;
//    }
    public String getInterestArea() {
        return interestArea;
    }
    public void setInterestArea(String interestArea) {
        this.interestArea = interestArea;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getFollowersNum() {
        return followersNum;
    }
    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }
    public int getFollowingNum() {
        return followingNum;
    }
    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
