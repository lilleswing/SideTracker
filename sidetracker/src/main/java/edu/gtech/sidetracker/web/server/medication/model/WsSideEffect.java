package edu.gtech.sidetracker.web.server.medication.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class WsSideEffect {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private String description;

    public WsSideEffect() {
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
}
