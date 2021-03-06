package edu.gtech.sidetracker.web.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="alarm")
public class Alarm {
    @Id
    @GeneratedValue
    private long id;

    @Basic
    @Column(name = "time")
    private String time;

    @Basic
    @Column(name = "day_of_week")
    private String day;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = UserMedication.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_medication_id")
    private UserMedication userMedication;

    public Alarm() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public UserMedication getUserMedication() {
        return userMedication;
    }

    public void setUserMedication(final UserMedication userMedication) {
        this.userMedication = userMedication;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, day);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Alarm other = (Alarm) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.time, other.time)
                && Objects.equals(this.day, other.day);
    }
}
