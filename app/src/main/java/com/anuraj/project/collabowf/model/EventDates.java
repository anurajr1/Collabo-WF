/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 1:51 PM
 *
 */

/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 11:34 AM
 *
 */

package com.anuraj.project.collabowf.model;

import java.util.ArrayList;

public class EventDates {
    private String date;
    private ArrayList<Events> eventsArrayList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Events> getEventsArrayList() {
        return eventsArrayList;
    }

    public void setEventsArrayList(ArrayList<Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
    }
}