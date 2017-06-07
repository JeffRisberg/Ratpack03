package com.incra.ratpack.models;

import com.incra.ratpack.database.DBItem;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jeff Risberg
 * @since 12/30/16
 */
@Entity
@Table(name="event")
public class Event extends DBItem {

    @Column(name = "event_date")
    private Date eventDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name="user_email")
    String userEmail;

    @Column()
    String page;

    @Column()
    String detail;

    public Event() {
    }

    public Event(Date eventDate, EventType eventType, String userEmail, String page, String detail) {
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.userEmail = userEmail;
        this.page = page;
        this.detail = detail;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
