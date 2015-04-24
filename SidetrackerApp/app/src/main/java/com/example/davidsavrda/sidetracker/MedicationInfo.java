package com.example.davidsavrda.sidetracker;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MedicationInfo {
    @JsonProperty("name")
    String name;
    String detail;

    ArrayList<AlarmInfo> alarms;
    ArrayList<SideEffect> sideEffects;

    public MedicationInfo(String name, String detail){
        this.name = name;
        this.detail = detail;
        this.alarms = new ArrayList<AlarmInfo>();
        this.sideEffects = new ArrayList<SideEffect>();
    }

    public MedicationInfo(){
        this.alarms = new ArrayList<AlarmInfo>();
        this.sideEffects = new ArrayList<SideEffect>();
    }

}
