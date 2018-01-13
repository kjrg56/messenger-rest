package com.kray.messenger.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String message;

    private String author;

    @Column(name = "creation_datetime")
    private Date creationDatetime;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private List<Link> links = new ArrayList<>();

    public Message() {
    }

    public Message(long id, String message, String author, Date creationDatetime) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.creationDatetime = creationDatetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Date creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    @XmlTransient
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", creationDatetime=" + creationDatetime +
                ", comments=" + comments +
                ", links=" + links +
                '}';
    }
}
