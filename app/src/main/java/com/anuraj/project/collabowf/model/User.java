package com.anuraj.project.collabowf.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String id;
    public String name;
    public String password;
    public String domain;
    public String mobile;
    public String mailid;
    public String qualification;
    public String certificate;
    public String propic;

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public User(String id, String name, String password, String domain, String mobile, String mailid, String qualification, String certificate, String propic) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.domain = domain;
        this.mobile = mobile;
        this.mailid = mailid;
        this.qualification = qualification;
        this.certificate = certificate;
        this.propic = propic;
    }
}
