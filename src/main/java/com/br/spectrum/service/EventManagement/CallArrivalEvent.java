package com.br.spectrum.service.EventManagement;

public class CallArrivalEvent extends SpectrumEvent{

    private Call call;

    public CallArrivalEvent(double timeIteration, Call call) {
        super(timeIteration);
        this.setEventType(SpectrumEventType.CALL_ARRIVAL);
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }
}
