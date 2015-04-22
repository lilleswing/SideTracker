package edu.gtech.sidetracker.web.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_medication")
public class UserMedication {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name="app_user_id")
    private AppUser appUser;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = Medication.class, fetch = FetchType.LAZY)
    @JoinColumn(name="medication_id")
    private Medication medication;

    @OneToMany(mappedBy = "userMedication", targetEntity = Alarm.class,
            fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Alarm> alarmSet = new HashSet<>();

    @OneToMany(mappedBy = "userMedication", targetEntity = SideEffect.class,
            fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<SideEffect> sideEffectSet = new HashSet<>();

    public UserMedication() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(final AppUser appUser) {
        this.appUser = appUser;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(final Medication medication) {
        this.medication = medication;
    }

    public Set<Alarm> getAlarmSet() {
        return alarmSet;
    }

    public void setAlarmSet(final Set<Alarm> alarmSet) {
        this.alarmSet = alarmSet;
    }

    public Set<SideEffect> getSideEffectSet() {
        return sideEffectSet;
    }

    public void setSideEffectSet(final Set<SideEffect> sideEffectSet) {
        this.sideEffectSet = sideEffectSet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUser, medication, alarmSet, sideEffectSet);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserMedication other = (UserMedication) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.appUser, other.appUser)
                && Objects.equals(this.medication, other.medication)
                && Objects.equals(this.alarmSet, other.alarmSet)
                && Objects.equals(this.sideEffectSet, other.sideEffectSet);
    }
}
