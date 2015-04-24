package com.example.davidsavrda.sidetracker.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppUser {

    @JsonProperty(value = "fhir_id")
    private String fhirId;

    public AppUser() {
    }

    public String getFhirId() {
        return fhirId;
    }

    public void setFhirId(final String fhirId) {
        this.fhirId = fhirId;
    }
}
