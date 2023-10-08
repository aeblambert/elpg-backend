package com.bookshare.dto;

public class NewUserRequest {
    private String email;
    private String password;
    private String nickname;
    private String district;
    private boolean consent;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() {return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getDistrict() {return district; }
    public void setDistrict(String district) { this.district = district; }

    public boolean getConsent() {return consent; }
    public void setConsent(boolean consent) { this.consent = consent; }
}
