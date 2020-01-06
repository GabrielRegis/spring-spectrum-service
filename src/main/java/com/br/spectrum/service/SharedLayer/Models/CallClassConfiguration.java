package com.br.spectrum.service.SharedLayer.Models;

public class CallClassConfiguration {
    private String id;
    private String name;
    private int minBandwidth;
    private int maxBandwidth;
    private int minHoldingTime;
    private int maxHoldingTime;
    private double frequency;
    private CallDegradationConfiguration degradationConfiguration;
    private String color;

    public CallClassConfiguration(){

    }

    public CallClassConfiguration(String name, int minBandwidth, int maxBandwidth, int minHoldingTime, int maxHoldingTime, double frequency){

    }

    public CallClassConfiguration(String name, int minBandwidth, int maxBandwidth, int minHoldingTime, int maxHoldingTime, double frequency, CallDegradationConfiguration degradationConfiguration, String color) {
        this.name = name;
        this.minBandwidth = minBandwidth;
        this.maxBandwidth = maxBandwidth;
        this.minHoldingTime = minHoldingTime;
        this.maxHoldingTime = maxHoldingTime;
        this.frequency = frequency;
        this.degradationConfiguration = degradationConfiguration;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getMinHoldingTime() {
        return minHoldingTime;
    }

    public void setMinHoldingTime(int minHoldingTime) {
        this.minHoldingTime = minHoldingTime;
    }

    public int getMaxHoldingTime() {
        return maxHoldingTime;
    }

    public void setMaxHoldingTime(int maxHoldingTime) {
        this.maxHoldingTime = maxHoldingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinBandwidth() {
        return minBandwidth;
    }

    public void setMinBandwidth(int minBandwidth) {
        this.minBandwidth = minBandwidth;
    }

    public int getMaxBandwidth() {
        return maxBandwidth;
    }

    public void setMaxBandwidth(int maxBandwidth) {
        this.maxBandwidth = maxBandwidth;
    }

    public CallDegradationConfiguration getDegradationConfiguration() {
        return degradationConfiguration;
    }

    public void setDegradationConfiguration(CallDegradationConfiguration degradationConfiguration) {
        this.degradationConfiguration = degradationConfiguration;
    }


}
