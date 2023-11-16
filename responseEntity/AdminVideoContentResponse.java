package com.webApp.responseEntity;

import java.util.Date;

public class AdminVideoContentResponse {

    private Integer vId;
    private String skills;
    private String title;
    private Integer likes;
    private String thumbNailUrl;
    private Boolean isPro;
    private Date postDate;
    private Boolean isLocked;

    private String createrFsName;
    private String createrLsName;
    private String createrDP;

    // No-arg constructor
    public AdminVideoContentResponse() {
    }

    // All-arg constructor
    public AdminVideoContentResponse(Integer vId, String skills, String title, Integer likes, String thumbNailUrl,
            Boolean isPro, Date postDate, Boolean isLocked, String createrFsName, String createrLsName,
            String createrDP) {
        this.vId = vId;
        this.skills = skills;
        this.title = title;
        this.likes = likes;
        this.thumbNailUrl = thumbNailUrl;
        this.isPro = isPro;
        this.postDate = postDate;
        this.isLocked = isLocked;
        this.createrFsName = createrFsName;
        this.createrLsName = createrLsName;
        this.createrDP = createrDP;
    }

    // Getters and setters
    public Integer getvId() {
        return vId;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public Boolean getIsPro() {
        return isPro;
    }

    public void setIsPro(Boolean isPro) {
        this.isPro = isPro;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getCreaterFsName() {
        return createrFsName;
    }

    public void setCreaterFsName(String createrFsName) {
        this.createrFsName = createrFsName;
    }

    public String getCreaterLsName() {
        return createrLsName;
    }

    public void setCreaterLsName(String createrLsName) {
        this.createrLsName = createrLsName;
    }

    public String getCreaterDP() {
        return createrDP;
    }

    public void setCreaterDP(String createrDP) {
        this.createrDP = createrDP;
    }
}