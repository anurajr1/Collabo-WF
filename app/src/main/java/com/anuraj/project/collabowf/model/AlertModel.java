/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 25/4/19 1:33 PM
 *
 */
package com.anuraj.project.collabowf.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AlertModel {

    public String name;
    public String id;
    public String alerts;

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

    public AlertModel(String id, String name, String alerts) {

        this.name = name;
        this.id = id;
        this.alerts = alerts;
    }

    public String getalerts() {
        return alerts;
    }

    public void setalerts(String alerts) {
        this.alerts = alerts;
    }



}
