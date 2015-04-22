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
import java.util.Objects;

@Entity
@Table(name="side_effect")
public class SideEffect {
    @Id
    @GeneratedValue
    private long id;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = UserMedication.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_medication_id")
    private UserMedication userMedication;

    public SideEffect() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public UserMedication getUserMedication() {
        return userMedication;
    }

    public void setUserMedication(final UserMedication userMedication) {
        this.userMedication = userMedication;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SideEffect other = (SideEffect) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.description, other.description);
    }
}
