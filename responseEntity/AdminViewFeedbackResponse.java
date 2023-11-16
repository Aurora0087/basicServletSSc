package com.webApp.responseEntity;

import java.util.Date;

public class AdminViewFeedbackResponse {

    private Integer id;
    private String title;
    private String details;
    private String type;
    private Date postDate;
    private Boolean isDone;

    private Integer userId;
    private String userFsname;
    private String userLsname;
    private String userDPString;

    // No-arg constructor
    public AdminViewFeedbackResponse() {
    }

    // All-arg constructor
    public AdminViewFeedbackResponse(Integer id, String title, String details, String type, Date postDate,
            Boolean isDone, Integer userId, String userFsname, String userLsname, String userDPString) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.type = type;
        this.postDate = postDate;
        this.isDone = isDone;
        this.userId = userId;
        this.userFsname = userFsname;
        this.userLsname = userLsname;
        this.userDPString = userDPString;
    }

    // Getter and setter methods for all fields

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFsname() {
        return userFsname;
    }

    public void setUserFsname(String userFsname) {
        this.userFsname = userFsname;
    }

    public String getUserLsname() {
        return userLsname;
    }

    public void setUserLsname(String userLsname) {
        this.userLsname = userLsname;
    }

    public String getUserDPString() {
        return userDPString;
    }

    public void setUserDPString(String userDPString) {
        this.userDPString = userDPString;
    }
}

