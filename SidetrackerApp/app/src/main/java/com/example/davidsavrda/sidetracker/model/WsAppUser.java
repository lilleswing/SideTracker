package com.example.davidsavrda.sidetracker.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WsAppUser {

    @JsonProperty(value = "fhir_id")
    private String fhirId;

    public WsAppUser() {
    }

    public String getFhirId() {
        return fhirId;
    }

    public void setFhirId(final String fhirId) {
        this.fhirId = fhirId;
    }
}
