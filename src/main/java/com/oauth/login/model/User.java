package com.oauth.login.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
//@Document
public class User {

    private String id;

    private String name;

    private String email;

    private String imageUrl;

    private Boolean emailVerified = false;

    @JsonIgnore
    private String password = null;

    private AuthProvider provider;

    private String providerId;
}
