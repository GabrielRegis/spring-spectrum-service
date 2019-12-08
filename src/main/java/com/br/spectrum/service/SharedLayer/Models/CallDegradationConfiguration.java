package com.br.spectrum.service.SharedLayer.Models;

public class CallDegradationConfiguration {
    private boolean isDegradationTolerant;
    private float bandwidthDegradationRate;
    private float delayToleranceRate;
    private float failureSurvivorBandwidthDegradationRate;
    private float failureDisruptedBandwidthDegradationRate;
    private float failureDisruptedDelayToleranceRate;


    public CallDegradationConfiguration() {
        this.isDegradationTolerant = false;
    }

    public CallDegradationConfiguration(float bandwidthDegradationRate, float delayToleranceRate, float failureSurvivorBandwidthDegradationRate, float failureDisruptedBandwidthDegradationRate, float failureDisruptedDelayToleranceRate) {
        this.isDegradationTolerant = true;
        this.bandwidthDegradationRate = bandwidthDegradationRate;
        this.delayToleranceRate = delayToleranceRate;
        this.failureSurvivorBandwidthDegradationRate = failureSurvivorBandwidthDegradationRate;
        this.failureDisruptedBandwidthDegradationRate = failureDisruptedBandwidthDegradationRate;
        this.failureDisruptedDelayToleranceRate = failureDisruptedDelayToleranceRate;
    }

    public boolean isDegradationTolerant() {
        return isDegradationTolerant;
    }

    public void setDegradationTolerant(boolean degradationTolerant) {
        isDegradationTolerant = degradationTolerant;
    }

    public float getBandwidthDegradationRate() {
        return bandwidthDegradationRate;
    }

    public void setBandwidthDegradationRate(float bandwidthDegradationRate) {
        this.bandwidthDegradationRate = bandwidthDegradationRate;
    }

    public float getDelayToleranceRate() {
        return delayToleranceRate;
    }

    public void setDelayToleranceRate(float delayToleranceRate) {
        this.delayToleranceRate = delayToleranceRate;
    }

    public float getFailureSurvivorBandwidthDegradationRate() {
        return failureSurvivorBandwidthDegradationRate;
    }

    public void setFailureSurvivorBandwidthDegradationRate(float failureSurvivorBandwidthDegradationRate) {
        this.failureSurvivorBandwidthDegradationRate = failureSurvivorBandwidthDegradationRate;
    }

    public float getFailureDisruptedBandwidthDegradationRate() {
        return failureDisruptedBandwidthDegradationRate;
    }

    public void setFailureDisruptedBandwidthDegradationRate(float failureDisruptedBandwidthDegradationRate) {
        this.failureDisruptedBandwidthDegradationRate = failureDisruptedBandwidthDegradationRate;
    }

    public float getFailureDisruptedDelayToleranceRate() {
        return failureDisruptedDelayToleranceRate;
    }

    public void setFailureDisruptedDelayToleranceRate(float failureDisruptedDelayToleranceRate) {
        this.failureDisruptedDelayToleranceRate = failureDisruptedDelayToleranceRate;
    }
}
