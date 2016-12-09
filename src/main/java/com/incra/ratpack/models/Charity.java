package com.incra.ratpack.models;

import com.incra.ratpack.database.DatedDatabaseItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Jeff Risberg
 * @since 06/03/16
 */
@Entity
public class Charity extends DatedDatabaseItem {

    @Column
    protected String name;
    @Column
    protected String ein;
    @Column
    protected String mission;

    public Charity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }
}
