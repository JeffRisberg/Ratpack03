package com.incra.ratpack.models;

import com.google.common.collect.Maps;
import com.incra.ratpack.database.DatabaseItem;
import com.incra.ratpack.database.DatedDatabaseItem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author Jeff Risberg
 * @since 12/30/16
 */
@Entity
@Table(name="logging_event")
public class LoggingEvent extends DatabaseItem {

    @Column(name = "event_date")
    private Date eventDate;

    @Column(name="user_email")
    String userEmail;

    @Column()
    String type; // master key

    @Column()
    String detail; // second key

    public LoggingEvent() {
    }

    public LoggingEvent(Date eventDate, String userEmail, String type, String detail) {
        this.eventDate = eventDate;
        this.userEmail = userEmail;
        this.type = type;
        this.detail = detail;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
