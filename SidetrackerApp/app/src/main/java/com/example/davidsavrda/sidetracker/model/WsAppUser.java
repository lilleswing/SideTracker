package com.example.davidsavrda.sidetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class WsAppUser {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    @JsonProperty(value = "user_name")
    private String userName;

    private String password;

    @JsonProperty(value = "fhir_id")
    private String fhirId;

    public WsAppUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getFhirId() {
        return fhirId;
    }

    public void setFhirId(final String fhirId) {
        this.fhirId = fhirId;
    }
}
