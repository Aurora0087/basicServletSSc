package com.webApp.responseEntity;

public class AdminUserViewResponse {
	private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImage;
    private String userRole;
    private Boolean isPro;
    private Boolean isLocked;

    // No-argument constructor
    public AdminUserViewResponse() {
    }
    
    

    // All-argument constructor
    public AdminUserViewResponse(Integer id, String firstName, String lastName, String email, String profileImage, String userRole, Boolean isPro, Boolean isLocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileImage = profileImage;
        this.userRole = userRole;
        this.isPro = isPro;
        this.isLocked = isLocked;
    }
    
    

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Boolean getIsPro() {
        return isPro;
    }

    public void setIsPro(Boolean isPro) {
        this.isPro = isPro;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }
}
