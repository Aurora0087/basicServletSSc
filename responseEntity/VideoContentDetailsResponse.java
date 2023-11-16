package com.webApp.responseEntity;

import java.util.Date;

public class VideoContentDetailsResponse {
	
	private Integer vId;
	private String skills;
	private String title;
	private Integer likes;
	private String thumbNailUrl;
	private Boolean isPro;
	private Date postDate;
	
	
	private String createrFsName;
	private String createrLsName;
	private String createrDP;
	
	
	//Getters
	public Integer getVid() {
		return this.vId;
	}
	public String getSkill() {
		return this.skills;
	}
	public String getTitle() {
		return this.title;
	}
	public Integer getLikes() {
		return this.likes;
	}
	public String getThumbNail() {
		return this.thumbNailUrl;
	}
	public Boolean getIsPro() {
		return this.isPro;
	}
	public Date getPostDate() {
		return postDate;
	}
	public String getCreaterFsName() {
		return createrFsName;
	}
	public String getCreaterLsName() {
		return createrLsName;
	}
	public String getCreaterDP() {
		return createrDP;
	}
	
	
	
	
	//setters
	public void setVid(Integer vid) {
		this.vId=vid;
	}
	public void setSkill(String skill) {
		this.skills=skill;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public void setLikes(Integer likes) {
		this.likes=likes;
	}
	public void setThumbNail(String thumbNail) {
		this.thumbNailUrl=thumbNail;
	}
	public void setIsPro(Boolean isPro) {
		this.isPro=isPro;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public void setCreaterFsName(String createrFsName) {
		this.createrFsName = createrFsName;
	}
	
	public void setCreaterLsName(String createrLsName) {
		this.createrLsName = createrLsName;
	}
	public void setCreaterDP(String createrDP) {
		this.createrDP = createrDP;
	}
	
	
	
	
	//Constructor
	public VideoContentDetailsResponse(Integer vid, String title, String skill, Integer likes, String thumbNail, Boolean isPro, Date postDate, String createrFsName, String createrLsName, String createrDP) {
		this.vId=vid;
		this.title=title;
		this.skills=skill;
		this.likes=likes;
		this.thumbNailUrl=thumbNail;
		this.isPro=isPro;
		this.postDate=postDate;
		this.createrFsName=createrFsName;
		this.createrLsName=createrLsName;
		this.createrDP=createrDP;
	}
	
	public VideoContentDetailsResponse () {
		
	}
}
