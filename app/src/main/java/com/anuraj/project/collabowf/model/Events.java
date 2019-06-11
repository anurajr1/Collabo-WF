/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 7/5/19 11:10 AM
 *
 */
package com.anuraj.project.collabowf.model;

public class Events {
    private String eventId;
    private String eventName;
    private String eventDate;
    private  String eventAlertDate;
    private  String eventStatus;
    private  String eventOPName;
    private  String eventSupervisorStatus;
    private  String eventOperatorSeen;

    public String getEventOperatorSeen() {
        return eventOperatorSeen;
    }

    public void setEventOperatorSeen(String eventOperatorSeen) {
        this.eventOperatorSeen = eventOperatorSeen;
    }

    public String getEventSupervisorStatus() {
        return eventSupervisorStatus;
    }

    public void setEventSupervisorStatus(String eventSupervisorStatus) {
        this.eventSupervisorStatus = eventSupervisorStatus;
    }

    public String getEventPrimaryKey() {
        return eventPrimaryKey;
    }

    public void setEventPrimaryKey(String eventPrimaryKey) {
        this.eventPrimaryKey = eventPrimaryKey;
    }

    private  String eventPrimaryKey;

    public String getEventOPName() {
        return eventOPName;
    }

    public void setEventOPName(String eventOPName) {
        this.eventOPName = eventOPName;
    }

    public String getEventAlertDate() {
        return eventAlertDate;
    }

    public void setEventAlertDate(String eventAlertDate) {
        this.eventAlertDate = eventAlertDate;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
