package edu.gtech.sidetracker.web.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.inject.Inject;

import java.util.Objects;

@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Basic
    @Column(name = "comment")
    private String comment;

    @Inject
    public Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(final AppUser appUser) {
        this.appUser = appUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.comment, other.comment);
    }
}
