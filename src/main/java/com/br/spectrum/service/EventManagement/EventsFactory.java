package com.br.spectrum.service.EventManagement;


import com.br.spectrum.service.Utils.PoissonRandomNumberGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class EventsFactory {

    private PriorityQueue<SpectrumEvent> simulationEvents;
    private long seed;

    public EventsFactory(ArrayList<Call> calls, int load, long seed) {

        Comparator<SpectrumEvent> eventsComparator = new Comparator<SpectrumEvent>() {
            @Override
            public int compare(SpectrumEvent eventA, SpectrumEvent eventB) {
                return eventA.getTimeIteration() < eventB.getTimeIteration() ? -1 : 1;
            }
        };

        this.simulationEvents = new PriorityQueue<SpectrumEvent>(eventsComparator);
        this.seed = seed;

        for(Call call : calls){
            double eventInstant = PoissonRandomNumberGenerator.getPoissonRandomNumber((double)load/1000, this.getSeed());
            CallArrivalEvent callArrivalEvent = new CallArrivalEvent(eventInstant, call);
            CallDepartureEvent callDepartureEvent = new CallDepartureEvent(eventInstant + call.getCallClass().getHoldingTime(), call);
            this.simulationEvents.add(callArrivalEvent);
            this.simulationEvents.add(callDepartureEvent);
        }
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public PriorityQueue<SpectrumEvent> getSimulationEvents() {
        return simulationEvents;
    }

    public void setSimulationEvents(PriorityQueue<SpectrumEvent> simulationEvents) {
        this.simulationEvents = simulationEvents;
    }
}
