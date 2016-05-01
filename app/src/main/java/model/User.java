package model;

public class User {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private int roleId;
    private int userInterest;
    private String city;
    private String state;
    private int followersNum; // Should be array of users or user id
    private int followingNum; // Should be array of users or user id
    private String imageURL;
    private String role;
    private String interest;
    private String password;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public int getUserInterest() {
        return userInterest;
    }
    public void setUserInterest(int userInterest) {
        this.userInterest = userInterest;
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
