package com.br.spectrum.service.EventManagement;

public class CriticalEventSnapshot {
    private SpectrumEvent event;
    private TopologySnapshot topologySnapshot;
    private double load;
    private int currentEvent;
    private int eventsAmount;

    public CriticalEventSnapshot(SpectrumEvent event, TopologySnapshot topologySnapshot,  int currentEvent, int eventsAmount) {
        this.event = event;
        this.topologySnapshot = topologySnapshot;
        this.load = 0;
        this.currentEvent = currentEvent;
        this.eventsAmount = eventsAmount;
    }


    public CriticalEventSnapshot(SpectrumEvent event, TopologySnapshot topologySnapshot) {
        this.event = event;
        this.topologySnapshot = topologySnapshot;
        this.load = 0;
        this.currentEvent = 0;
        this.eventsAmount = 0;
    }

    public SpectrumEvent getEvent() {
        return event;
    }

    public void setEvent(SpectrumEvent event) {
        this.event = event;
    }

    public TopologySnapshot getTopologySnapshot() {
        return topologySnapshot;
    }

    public void setTopologySnapshot(TopologySnapshot topologySnapshot) {
        this.topologySnapshot = topologySnapshot;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public int getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(int currentEvent) {
        this.currentEvent = currentEvent;
    }

    public int getEventsAmount() {
        return eventsAmount;
    }

    public void setEventsAmount(int eventsAmount) {
        this.eventsAmount = eventsAmount;
    }
}
