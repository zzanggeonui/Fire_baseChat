package com.example.fire_basechat.model;

public class User {
    // 사ㅣ용자 계정 정보 모델 클래스
    private String emailId;
    private  String password;

    private  String message;

public User(String emailId, String message) {
        this.emailId = emailId;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // 계정 고유 토큰정보
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
