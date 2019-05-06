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

    public AlertModel(String id, String name, String status) {

        this.name = name;
        this.id = id;
        this.status = status;
    }

}
