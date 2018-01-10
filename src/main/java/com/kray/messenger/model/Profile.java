package com.kray.messenger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table (name = "profiles")
public class Profile {

    @Id
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "creation_datetime")
    private Date creationDatetime;

    public Profile() {
    }

    public Profile(String username, String firstName, String lastName, Date creationDatetime) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDatetime = creationDatetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Date creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", creationDatetime=" + creationDatetime +
                '}';
    }

}
