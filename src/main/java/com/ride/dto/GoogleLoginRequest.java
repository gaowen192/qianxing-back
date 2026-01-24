package com.ride.dto;

import java.time.LocalDateTime;

/**
 * Google用户登录请求DTO
 * 
 * @author 千行团队
 * @version 1.0.0
 */
public class GoogleLoginRequest {
    
    private String googleId;
    private String email;
    private String username;
    private String avatar;
    private String googleAccessToken;
    private String googleRefreshToken;
    private LocalDateTime googleTokenExpiry;
    
    // Getter和Setter方法
    public String getGoogleId() {
        return googleId;
    }
    
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public String getGoogleAccessToken() {
        return googleAccessToken;
    }
    
    public void setGoogleAccessToken(String googleAccessToken) {
        this.googleAccessToken = googleAccessToken;
    }
    
    public String getGoogleRefreshToken() {
        return googleRefreshToken;
    }
    
    public void setGoogleRefreshToken(String googleRefreshToken) {
        this.googleRefreshToken = googleRefreshToken;
    }
    
    public LocalDateTime getGoogleTokenExpiry() {
        return googleTokenExpiry;
    }
    
    public void setGoogleTokenExpiry(LocalDateTime googleTokenExpiry) {
        this.googleTokenExpiry = googleTokenExpiry;
    }
}