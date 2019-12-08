package com.br.spectrum.service.EventManagement;

public class CallDepartureEvent extends SpectrumEvent {

    private Call call;

    public CallDepartureEvent(double timeIteration, Call call) {
        super(timeIteration);
        this.setEventType(SpectrumEventType.CALL_DEPARTURE);
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

}
