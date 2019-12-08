package com.br.spectrum.service.EventManagement.Interfaces;

import com.br.spectrum.service.EventManagement.Call;
import com.br.spectrum.service.EventManagement.CallArrivalEvent;
import com.br.spectrum.service.EventManagement.CallDepartureEvent;
import com.br.spectrum.service.EventManagement.SpectrumEvent;

public interface ControlPlaneInterface {

    public void eventArrival(SpectrumEvent event);
    public void callArrival(CallArrivalEvent callArrivalEvent);
    public void callDeparture(CallDepartureEvent callDepartureEvent);
    public void allocateCall(Call call);
    public void freeCall(Call call);
    public void blockCall(Call call);
    public void dropCall(Call call);
    public void delayCall(Call call);
    public void createIterationStatistics(SpectrumEvent event);
    public void createCriticalEvents(SpectrumEvent event);

}
