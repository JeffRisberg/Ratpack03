package com.incra.ratpack.models;

import com.google.common.collect.Maps;
import com.incra.ratpack.database.DatedDatabaseItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Jeff Risberg
 * @since 12/30/16
 */
@Entity
public class LoggingEvent extends DatedDatabaseItem {

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @Column()
    String type; // master key

    @Column()
    String detail; // second key

    public LoggingEvent() {
    }

    public LoggingEvent(User user, String type, String detail) {
        this.user = user;
        this.type = type;
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
