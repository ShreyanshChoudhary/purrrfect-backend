package com.Purrrfect.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)   // ✅ Auto-generate UUID for primary key
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String userId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, name = "name", length = 100)
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setAccountCreatedDate(LocalDate accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Column(nullable = false)
    private String provider; // LOCAL / GOOGLE

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @ToString.Exclude
    @Column(nullable = false)
    private String password;


    @Column(nullable = true, length = 10)
    private String phoneNumber;

    @Size(max = 255, message = "Address must be less than 255 characters")
    @Column(nullable = true)
    private String address;
    @Column(nullable = true)
    private String providerId; // Google "sub"

    @Column(nullable = true)
    private String picture;

    @Column(nullable = false)
    private Boolean emailVerified;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @Column(name = "account_created_date", nullable = false)
    private LocalDate accountCreatedDate;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = true)
    private String profileImageUrl;

    @PrePersist
    public void prePersist() {
        if (this.accountCreatedDate == null)
            this.accountCreatedDate = LocalDate.now();

        if (this.isActive == null)
            this.isActive = true;

        if (this.username == null)
            this.username = this.email;

        if (this.provider == null)
            this.provider = "LOCAL";

        if (this.emailVerified == null)
            this.emailVerified = false;
    }

}
