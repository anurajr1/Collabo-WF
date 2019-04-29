/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 30/4/19 1:33 PM
 * *
 */

package com.anuraj.project.collabowf.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class OperatorList {

    public String listmap;

    public String getListmap() {
        return listmap;
    }

    public void setListmap(String listmap) {
        this.listmap = listmap;
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

    public String id;
    public String name;

    public OperatorList() {
    }

}
