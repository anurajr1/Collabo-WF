/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 3:50 PM
 *
 */
package com.anuraj.project.collabowf.model;

import java.util.ArrayList;

public class EventInformation {

    private ArrayList<EventDates> eventsDatesList = new ArrayList<>();

    public ArrayList<EventDates> getEventsDatesList() {
        return eventsDatesList;
    }

    public void setEventsDatesList(ArrayList<EventDates> eventsDatesList) {
        this.eventsDatesList = eventsDatesList;
    }
}