package com.br.spectrum.service.EventManagement;

public class CriticalFragmentationEventSnapshot extends CriticalEventSnapshot {

    public double gainRate;
    public double fragmentation;
    public double absoluteDifference;

    public CriticalFragmentationEventSnapshot(SpectrumEvent event, TopologySnapshot topologySnapshot, double currentFragmentation, double previousFragmentation, int currentEvent, int eventsAmount) {
        super(event, topologySnapshot, currentEvent, eventsAmount);
        this.absoluteDifference = Math.abs(currentFragmentation - previousFragmentation);
        this.fragmentation = currentFragmentation;
        this.gainRate = currentFragmentation/previousFragmentation;
    }

    public CriticalFragmentationEventSnapshot(SpectrumEvent event, TopologySnapshot topologySnapshot) {
        super(event, topologySnapshot);
    }

    public double getGainRate() {
        return gainRate;
    }

    public void setGainRate(double gainRate) {
        this.gainRate = gainRate;
    }

    public double getFragmentation() {
        return fragmentation;
    }

    public void setFragmentation(double fragmentation) {
        this.fragmentation = fragmentation;
    }

    public double getAbsoluteDifference() {
        return absoluteDifference;
    }

    public void setAbsoluteDifference(double absoluteDifference) {
        this.absoluteDifference = absoluteDifference;
    }
}
