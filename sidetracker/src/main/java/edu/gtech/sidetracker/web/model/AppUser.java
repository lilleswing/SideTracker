package edu.gtech.sidetracker.web.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="app_user")
public class AppUser {
    @Id
    @GeneratedValue
    private long id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "appUser", targetEntity = Comment.class,
            fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Comment> commentSet;

    public AppUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(final Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
