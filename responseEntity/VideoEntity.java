package com.webApp.responseEntity;

import java.util.Date;

public class VideoEntity {
	
	private Integer vId;
	private String skills;
	private String title;
	private Integer likes;
	private Integer views;
	private String videoUrl;
	private Date postDate;
	
	
	private String createrFsName;
	private String createrLsName;
	private String createrDP;

	
	// No-args constructor
    public VideoEntity() {
    }

    // All-args constructor
    public VideoEntity(Integer vId, String skills, String title, Integer likes, Integer views, 
    					String videoUrl, Date postDate, String createrFsName, String createrLsName, String createrDP) {
        this.vId = vId;
        this.skills = skills;
        this.title = title;
        this.likes = likes;
        this.views = views;
        this.videoUrl = videoUrl;
        this.postDate = postDate;
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
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
