package com.br.spectrum.service.EventManagement;

public class CriticalBPEventSnapshot extends CriticalEventSnapshot {
    public double gainRate;
    public double blockedAmount;
    public double absoluteDifference;

    public CriticalBPEventSnapshot(SpectrumEvent event, TopologySnapshot topologySnapshot, double currentBlockedCalls, double previousBlockedClass, int currentEvent, int eventsAmount) {
        super(event, topologySnapshot, currentEvent, eventsAmount);
        this.absoluteDifference = Math.abs(currentBlockedCalls - previousBlockedClass);
        this.blockedAmount = currentBlockedCalls;
        this.gainRate = currentBlockedCalls/previousBlockedClass;
    }

    public CriticalBPEventSnapshot(SpectrumEvent event, TopologySnapshot topologySnapshot) {
        super(event, topologySnapshot);
    }

    public double getGainRate() {
        return gainRate;
    }

    public void setGainRate(double gainRate) {
        this.gainRate = gainRate;
    }

    public double getBlockedAmount() {
        return blockedAmount;
    }

    public void setBlockedAmount(double blockedAmount) {
        this.blockedAmount = blockedAmount;
    }

    public double getAbsoluteDifference() {
        return absoluteDifference;
    }

    public void setAbsoluteDifference(double absoluteDifference) {
        this.absoluteDifference = absoluteDifference;
    }
}


