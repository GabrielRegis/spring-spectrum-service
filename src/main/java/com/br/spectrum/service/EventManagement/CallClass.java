package com.br.spectrum.service.EventManagement;

import com.br.spectrum.service.SharedLayer.Models.CallDegradationConfiguration;

import java.util.UUID;

public class CallClass {

    private String id;
    private String className;
    private double frequency;
    private int requiredBandwidth;
    private double holdingTime;
    private CallDegradationConfiguration degradationConfiguration;

    public CallClass(String className, double frequency, int requiredBandwidth, double holdingTime, CallDegradationConfiguration degradationConfiguration) {
        this.id = UUID.randomUUID().toString();
        this.className = className;
        this.frequency = frequency;
        this.requiredBandwidth = requiredBandwidth;
        this.holdingTime = holdingTime;
        this.degradationConfiguration = degradationConfiguration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getRequiredBandwidth() {
        return requiredBandwidth;
    }

    public void setRequiredBandwidth(int requiredBandwidth) {
        this.requiredBandwidth = requiredBandwidth;
    }

    public double getHoldingTime() {
        return holdingTime;
    }

    public void setHoldingTime(double holdingTime) {
        this.holdingTime = holdingTime;
    }

    public CallDegradationConfiguration getDegradationConfiguration() {
        return degradationConfiguration;
    }

    public void setDegradationConfiguration(CallDegradationConfiguration degradationConfiguration) {
        this.degradationConfiguration = degradationConfiguration;
    }
}
