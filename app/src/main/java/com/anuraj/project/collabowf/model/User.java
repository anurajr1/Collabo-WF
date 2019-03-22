package com.anuraj.project.collabowf.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String id;
    public String name;
    public String password;
    public String domain;

    public User() {
    }

    public User(String id, String name, String password, String domain) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.domain = domain;
    }
}
