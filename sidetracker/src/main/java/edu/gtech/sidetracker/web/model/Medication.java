package edu.gtech.sidetracker.web.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="medication")
public class Medication {
    @Id
    @GeneratedValue
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    public Medication() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Medication other = (Medication) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.name, other.name);
    }
}
