package com.kray.messenger.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    @Column(name = "creation_datetime")
    private Date creationDatetime;

    private String author;

    @ManyToOne(cascade = CascadeType.ALL)
    private Message message;

    public Comment() {
    }

    public Comment(long id, String content, Date creationDatetime, String author) {
        this.id = id;
        this.content = content;
        this.creationDatetime = creationDatetime;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setCommentContent(String content) {
        this.content = content;
    }

    public Date getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Date creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlTransient
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentContent='" + content + '\'' +
                ", creationDatetime=" + creationDatetime +
                ", author='" + author + '\'' +
                ", message id='" + message.getId() + '\'' +
                '}';
    }

}
