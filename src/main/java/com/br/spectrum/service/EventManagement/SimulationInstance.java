package com.br.spectrum.service.EventManagement;

import com.br.spectrum.service.PhysicalLayer.EONNode;
import com.br.spectrum.service.PhysicalLayer.EONPhysicalTopology;
import com.br.spectrum.service.SpectrumManagement.SampleRSA;
import com.br.spectrum.service.domain.SimulationInstanceSummary;
import com.br.spectrum.service.services.SimulationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class SimulationInstance {
    private int callsNumber;
    private double load;
    private int seed;
    private ArrayList<Call> calls;
    private ArrayList<CallClass> callClasses;
    private ControlPlane controlPlane;
    private EventsFactory eventsFactory;
    private EONPhysicalTopology eonPhysicalTopology;
    public static Random randomGenerator;

    public SimulationInstance(int callsNumber, double load, int seed, ArrayList<CallClass> callClasses, EONPhysicalTopology eonPhysicalTopology, SimulationService service) {
        this.load = load;
        this.seed = seed;
        randomGenerator = new Random(seed);
        this.callClasses = callClasses;
        this.eonPhysicalTopology = eonPhysicalTopology;

        this.calls = new ArrayList<Call>();
        for(CallClass callClass : this.getCallClasses()){
            int classCallsNumber = (int) (callClass.getFrequency()*callsNumber);

            for(int i = 0; i < classCallsNumber; i++){
                LinkedList<EONNode> nodes = new LinkedList<EONNode>(eonPhysicalTopology.vertexSet());
                Collections.shuffle(nodes, randomGenerator);
                Call call = new Call(callClass, nodes.pop(), nodes.pop());
                this.getCalls().add(call);
            }
        }

        this.callsNumber = this.getCalls().size();
        eventsFactory = new EventsFactory(this.getCalls(), (int)load, seed);
        controlPlane = new ControlPlane(eonPhysicalTopology, new SampleRSA(), service, eventsFactory.getSimulationEvents().size());

    }

    public SimulationInstanceSummaryWithControlPlane runSimulationInstance() {
        while (eventsFactory.getSimulationEvents().size() > 0){
            SpectrumEvent spectrumEvent = eventsFactory.getSimulationEvents().poll();
            controlPlane.eventArrival(spectrumEvent);
        }

        System.out.println("RESULTADOS");
        System.out.println("Chamadas bloqueadas " + controlPlane.getBlockedCalls().size());
        System.out.println("Chamadas atendidas " + controlPlane.getCurrentCalls());
        return new SimulationInstanceSummaryWithControlPlane( controlPlane, new SimulationInstanceSummary(this.getLoad(), controlPlane, callClasses));
    }

    public int getCallsNumber() {
        return callsNumber;
    }

    public void setCallsNumber(int callsNumber) {
        this.callsNumber = callsNumber;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public ArrayList<Call> getCalls() {
        return calls;
    }

    public void setCalls(ArrayList<Call> calls) {
        this.calls = calls;
    }

    public ArrayList<CallClass> getCallClasses() {
        return callClasses;
    }

    public void setCallClasses(ArrayList<CallClass> callClasses) {
        this.callClasses = callClasses;
    }

    public ControlPlane getControlPlane() {
        return controlPlane;
    }

    public void setControlPlane(ControlPlane controlPlane) {
        this.controlPlane = controlPlane;
    }

    public EventsFactory getEventsFactory() {
        return eventsFactory;
    }

    public void setEventsFactory(EventsFactory eventsFactory) {
        this.eventsFactory = eventsFactory;
    }

    public EONPhysicalTopology getEonPhysicalTopology() {
        return eonPhysicalTopology;
    }

    public void setEonPhysicalTopology(EONPhysicalTopology eonPhysicalTopology) {
        this.eonPhysicalTopology = eonPhysicalTopology;
    }
}
