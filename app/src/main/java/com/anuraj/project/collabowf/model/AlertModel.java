/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 7/5/19 1:52 PM
 *
 */
package com.anuraj.project.collabowf.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AlertModel {

    public String name;
    public String id;
    public String status;
    public String operatorseen;
    public String supervisorseen;
    public String selecteddate;

    public String getOperatorseen() {
        return operatorseen;
    }

    public void setOperatorseen(String operatorseen) {
        this.operatorseen = operatorseen;
    }

    public String getSupervisorseen() {
        return supervisorseen;
    }

    public void setSupervisorseen(String supervisorseen) {
        this.supervisorseen = supervisorseen;
    }

    public String getSelecteddate() {
        return selecteddate;
    }

    public void setSelecteddate(String selecteddate) {
        this.selecteddate = selecteddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public AlertModel() {
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

    public AlertModel(String id, String name, String status, String operatorseen, String supervisorseen, String selecteddate) {

        this.name = name;
        this.id = id;
        this.status = status;
        this.operatorseen =operatorseen;
        this.supervisorseen = supervisorseen;
        this.selecteddate = selecteddate;
    }

}
