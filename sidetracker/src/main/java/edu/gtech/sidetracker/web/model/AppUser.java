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
import java.util.Objects;
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

    @Basic
    @Column(name = "fhir_id")
    private String fhirId;

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

    public String getFhirId() {
        return fhirId;
    }

    public void setFhirId(final String fhirId) {
        this.fhirId = fhirId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fhirId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AppUser other = (AppUser) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.username, other.username)
                && Objects.equals(this.password, other.password)
                && Objects.equals(this.fhirId, other.fhirId);
    }
}
