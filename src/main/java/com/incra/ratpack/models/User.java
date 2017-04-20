package com.incra.ratpack.models;

import com.google.common.collect.Maps;
import com.incra.ratpack.database.DatedDatabaseItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Map;

/**
 * @author Jeff Risberg
 * @since 11/26/16
 */
@Entity
public class User extends DatedDatabaseItem {
    @Column(name = "email")
    protected String email;

    @Column(name = "firstname")
    protected String firstname;

    @Column(name = "lastname")
    protected String lastname;

    @Column(name = "address1")
    protected String addressLine1;

    @Column(name = "address2")
    protected String addressLine2;

    @Column(name = "city")
    protected String city;

    @Column(name = "state")
    protected String state;

    public User() {
    }

    public User(String email, String firstname, String lastname,
                String addressLine1, String addressLine2,
                String city, String state) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> result = Maps.newHashMap();

        result.put("id", getId());
        result.put("email", getEmail());
        result.put("firstname", getFirstname());
        result.put("lastname", getLastname());
        result.put("addressLine1", getAddressLine1());
        result.put("addressLine2", getAddressLine2());
        result.put("city", getCity());
        result.put("state", getState());

        return result;
    }
}
