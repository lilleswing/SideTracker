package com.example.davidsavrda.sidetracker;

import java.util.ArrayList;

/**
 * Created by davidsavrda on 4/18/15.
 */
public class MedicationInfo {
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
