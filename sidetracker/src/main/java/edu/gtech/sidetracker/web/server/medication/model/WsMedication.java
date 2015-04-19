package edu.gtech.sidetracker.web.server.medication.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class WsMedication {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private String name;

    private List<WsAlarm> alarms = new ArrayList<>();

    @JsonProperty("side_effects")
    private List<WsSideEffect> sideEffects = new ArrayList<>();

    public WsMedication() {
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

    public List<WsAlarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(final List<WsAlarm> alarms) {
        this.alarms = alarms;
    }

    public List<WsSideEffect> getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(final List<WsSideEffect> sideEffects) {
        this.sideEffects = sideEffects;
    }
}
