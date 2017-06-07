package com.incra.ratpack.models;

import com.incra.ratpack.database.DatedDBItem;

import javax.persistence.*;

/**
 * @author Jeff Risberg
 * @since 12/30/16
 */
@Entity
@Table(name="donation")
public class Donation extends DatedDBItem {

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    @Column(name="charity_name")
    String charityName;

    @Column()
    Integer amount;

    public Donation() {
    }

    public Donation(User user, String charityName, Integer amount) {
        this.user = user;
        this.charityName = charityName;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
