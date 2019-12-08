package com.br.spectrum.service.EventManagement;

public class SpectrumEvent {
    private double timeIteration;
    private SpectrumEventType eventType;

    public SpectrumEvent(double timeIteration) {
        this.timeIteration = timeIteration;
    }

    public double getTimeIteration() {
        return timeIteration;
    }

    public void setTimeIteration(double timeIteration) {
        this.timeIteration = timeIteration;
    }

    public SpectrumEventType getEventType() {
        return eventType;
    }

    public void setEventType(SpectrumEventType eventType) {
        this.eventType = eventType;
    }
}
