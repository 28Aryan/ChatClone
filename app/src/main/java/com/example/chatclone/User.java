package com.example.chatclone;

//user.java file is for setting the data in the database

public class User {
    String userId, userName, email, password, profilePic, lastMes, status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLastMes() {
        return lastMes;
    }

    public void setLastMes(String lastMes) {
        this.lastMes = lastMes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String id, String user, String email, String pass, String profilePic, String status){
        this.userId = id;
        this.userName = user;
        this.email = email;
        this.password = pass;
        this.profilePic = profilePic;
        this.status = status;

    }
}
