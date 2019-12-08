package com.br.spectrum.service.EventManagement;


import com.br.spectrum.service.EventManagement.Interfaces.ControlPlaneInterface;
import com.br.spectrum.service.PhysicalLayer.EONPhysicalTopology;
import com.br.spectrum.service.SpectrumManagement.Interfaces.RSA;
import com.br.spectrum.service.VirtualLayer.EONVirtualTopology;
import com.br.spectrum.service.domain.SimulationIterationStatistics;
import com.br.spectrum.service.services.SimulationService;

import java.util.HashMap;

public class ControlPlane implements ControlPlaneInterface {

    private SimulationService service;
    private EONPhysicalTopology eonPhysicalTopology;
    private EONVirtualTopology eonVirtualTopology;
    private HashMap<String, Call> currentCalls;
    private HashMap<String, Call> delayedCalls;
    private HashMap<String, Call> blockedCalls;
    private HashMap<String, Call> droppedCalls;
    private HashMap<String, Call> allocatedCalls;
    private HashMap<String, Call> successfulCalls;
    private double currentBlockedBandwidth;
    private int currentBlockedCallsAmount;
    private int previousBlockedCallsAmount;
    private RSA simulationRSA;
    private HashMap<String, SimulationIterationStatistics> simulationStatistics;
    private CriticalBPEventSnapshot mostCriticalBPEvent;
    private CriticalFragmentationEventSnapshot mostCriticalFragmentationEvent;
    private double currentFragmentation;
    private double previousFragmentation;
    private int eventCount;
    private int eventsAmount;
    private SpectrumEvent currentEvent;

    public ControlPlane(EONPhysicalTopology eonPhysicalTopology, RSA simulationRSA, SimulationService service, int eventsAmount){
        this.eonPhysicalTopology = eonPhysicalTopology;
        this.eonVirtualTopology = new EONVirtualTopology();
        this.currentCalls = new HashMap<String, Call>();
        this.delayedCalls = new HashMap<String, Call>();
        this.blockedCalls = new HashMap<String, Call>();
        this.droppedCalls = new HashMap<String, Call>();
        this.allocatedCalls = new HashMap<String, Call>();
        this.successfulCalls = new HashMap<String, Call>();
        this.simulationRSA = simulationRSA;
        this.simulationStatistics = new HashMap<String, SimulationIterationStatistics>();
        this.service = service;
        this.eventCount = 0;
        this.eventsAmount = eventsAmount;
        this.currentBlockedBandwidth = 0;
        this.currentBlockedCallsAmount = 0;
        this.previousBlockedCallsAmount = Integer.MAX_VALUE;
        this.mostCriticalBPEvent = null;
        this.currentEvent = null;
        this.mostCriticalFragmentationEvent = null;
        this.currentFragmentation = 0;
        this.previousFragmentation = Double.MAX_VALUE;
    }

    @Override
    public void createCriticalEvents(SpectrumEvent event){
//        if(this.eventCount == (int)(eventsAmount/4)){
            if(this.getPreviousBlockedCallsAmount() == -1){
                this.previousBlockedCallsAmount = 0;
                this.currentBlockedCallsAmount = 0;
                this.previousFragmentation = 0;
                this.currentFragmentation = 0;
                return;
            }
            if(this.currentBlockedCallsAmount > this.previousBlockedCallsAmount || this.previousBlockedCallsAmount == Integer.MAX_VALUE){
                TopologySnapshot topologySnapshot = new TopologySnapshot(this.getEonPhysicalTopology());
                CriticalBPEventSnapshot criticalBPEventSnapshot = new CriticalBPEventSnapshot(event, topologySnapshot, this.currentBlockedCallsAmount, this.previousBlockedCallsAmount, this.eventCount, this.eventsAmount);
                this.mostCriticalBPEvent = criticalBPEventSnapshot;
                this.previousBlockedCallsAmount = this.currentBlockedCallsAmount;
                this.currentBlockedCallsAmount = 0;
                this.currentBlockedBandwidth = 0;
            }

            // Fragmentation calculation
            this.currentFragmentation = this.getEonPhysicalTopology().getTopologyFragmentation();
            if((this.currentFragmentation > this.previousFragmentation || this.previousFragmentation == Double.MAX_VALUE) && this.currentFragmentation != 1){
                TopologySnapshot topologySnapshot = new TopologySnapshot(this.getEonPhysicalTopology());
                CriticalFragmentationEventSnapshot criticalFragmentationEventSnapshot = new CriticalFragmentationEventSnapshot(event, topologySnapshot, this.currentFragmentation, this.previousFragmentation, this.eventCount, this.eventsAmount);
                this.mostCriticalFragmentationEvent = criticalFragmentationEventSnapshot;
                this.previousFragmentation = this.currentFragmentation;
            }
//        }
    }

    @Override
    public void createIterationStatistics(SpectrumEvent event){
        if(eventCount == 1 || eventCount == eventsAmount/2 || eventCount == eventsAmount){
            SimulationIterationStatistics iterationStatistics = new SimulationIterationStatistics( event.getTimeIteration(), this);
            this.getSimulationStatistics().put(iterationStatistics.getId(), iterationStatistics);
        }
    }

    @Override
    public void eventArrival(SpectrumEvent event) {
        this.currentEvent = event;
        switch (event.getEventType()){
            case CALL_ARRIVAL:
                CallArrivalEvent callArrivalEvent = (CallArrivalEvent) event;
                this.callArrival(callArrivalEvent);
                this.simulationRSA.callArrival(this, this.getEonPhysicalTopology(), this.getEonVirtualTopology(), callArrivalEvent.getCall());
                break;
            case CALL_DEPARTURE:
                CallDepartureEvent callDepartureEvent = (CallDepartureEvent) event;
                this.callDeparture(callDepartureEvent);
                break;
        }
        eventCount++;
        this.createIterationStatistics(event);
    }

    @Override
    public void callArrival(CallArrivalEvent callArrivalEvent) {

    }

    @Override
    public void callDeparture(CallDepartureEvent callDepartureEvent) {
        Call currentCall = this.getCurrentCalls().get(callDepartureEvent.getCall().getId());
        // Check if the current call exists (Not blocked calls)
        if(currentCall != null){
            this.freeCall(currentCall);
        }
    }

    @Override
    public void allocateCall(Call call) {
        int lastFreeSlots = this.getEonPhysicalTopology().getFreeSlotsSize();
        this.getCurrentCalls().put(call.getId(), call);
        call.getLightpaths().forEach((id, lightpathInterface) -> {
            lightpathInterface.allocateLightpath();
        });

        this.getAllocatedCalls().put(call.getId(), call);
        this.getSuccessfulCalls().put(call.getId(), call);

        int currentFreeSlots = this.getEonPhysicalTopology().getFreeSlotsSize();

    }

    @Override
    public void freeCall(Call call) {
        int lastFreeSlots = this.getEonPhysicalTopology().getFreeSlotsSize();
        call.getLightpaths().forEach((id, lightpathInterface) -> {
            lightpathInterface.freeLightpath();
        });
        this.getCurrentCalls().remove(call.getId());
        int currentFreeSlots = this.getEonPhysicalTopology().getFreeSlotsSize();
    }

    @Override
    public void blockCall(Call call) {
        this.createCriticalEvents(this.currentEvent);
        this.blockedCalls.put(call.getId(), call);
        this.currentBlockedCallsAmount ++;
        this.currentBlockedBandwidth += call.getCallClass().getRequiredBandwidth();
        if(this.getSuccessfulCalls().containsKey(call.getId())){
            this.getSuccessfulCalls().remove(call.getId());
        }
    }

    @Override
    public void dropCall(Call call) {

    }

    @Override
    public void delayCall(Call call) {
        this.delayedCalls.put(call.getId(), call);
    }

    public CriticalFragmentationEventSnapshot getMostCriticalFragmentationEvent() {
        return mostCriticalFragmentationEvent;
    }

    public void setMostCriticalFragmentationEvent(CriticalFragmentationEventSnapshot mostCriticalFragmentationEvent) {
        this.mostCriticalFragmentationEvent = mostCriticalFragmentationEvent;
    }

    public double getCurrentFragmentation() {
        return currentFragmentation;
    }

    public void setCurrentFragmentation(int currentFragmentation) {
        this.currentFragmentation = currentFragmentation;
    }

    public double getPreviousFragmentation() {
        return previousFragmentation;
    }

    public void setPreviousFragmentation(int previousFragmentation) {
        this.previousFragmentation = previousFragmentation;
    }

    public void setCurrentFragmentation(double currentFragmentation) {
        this.currentFragmentation = currentFragmentation;
    }

    public void setPreviousFragmentation(double previousFragmentation) {
        this.previousFragmentation = previousFragmentation;
    }

    public SimulationService getService() {
        return service;
    }

    public void setService(SimulationService service) {
        this.service = service;
    }

    public HashMap<String, Call> getAllocatedCalls() {
        return allocatedCalls;
    }

    public void setAllocatedCalls(HashMap<String, Call> allocatedCalls) {
        this.allocatedCalls = allocatedCalls;
    }

    public HashMap<String, Call> getSuccessfulCalls() {
        return successfulCalls;
    }

    public void setSuccessfulCalls(HashMap<String, Call> successfulCalls) {
        this.successfulCalls = successfulCalls;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public int getEventsAmount() {
        return eventsAmount;
    }

    public void setEventsAmount(int eventsAmount) {
        this.eventsAmount = eventsAmount;
    }

    public HashMap<String, SimulationIterationStatistics> getSimulationStatistics() {
        return simulationStatistics;
    }

    public void setSimulationStatistics(HashMap<String, SimulationIterationStatistics> simulationStatistics) {
        this.simulationStatistics = simulationStatistics;
    }

    public EONPhysicalTopology getEonPhysicalTopology() {
        return eonPhysicalTopology;
    }

    public void setEonPhysicalTopology(EONPhysicalTopology eonPhysicalTopology) {
        this.eonPhysicalTopology = eonPhysicalTopology;
    }

    public EONVirtualTopology getEonVirtualTopology() {
        return eonVirtualTopology;
    }

    public void setEonVirtualTopology(EONVirtualTopology eonVirtualTopology) {
        this.eonVirtualTopology = eonVirtualTopology;
    }

    public double getCurrentBlockedBandwidth() {
        return currentBlockedBandwidth;
    }

    public void setCurrentBlockedBandwidth(double currentBlockedBandwidth) {
        this.currentBlockedBandwidth = currentBlockedBandwidth;
    }

    public int getCurrentBlockedCallsAmount() {
        return currentBlockedCallsAmount;
    }

    public void setCurrentBlockedCallsAmount(int currentBlockedCallsAmount) {
        this.currentBlockedCallsAmount = currentBlockedCallsAmount;
    }

    public int getPreviousBlockedCallsAmount() {
        return previousBlockedCallsAmount;
    }

    public void setPreviousBlockedCallsAmount(int previousBlockedCallsAmount) {
        this.previousBlockedCallsAmount = previousBlockedCallsAmount;
    }

    public CriticalBPEventSnapshot getMostCriticalBPEvent() {
        return mostCriticalBPEvent;
    }

    public void setMostCriticalBPEvent(CriticalBPEventSnapshot mostCriticalBPEvent) {
        this.mostCriticalBPEvent = mostCriticalBPEvent;
    }

    public SpectrumEvent getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(SpectrumEvent currentEvent) {
        this.currentEvent = currentEvent;
    }

    public HashMap<String, Call> getCurrentCalls() {
        return currentCalls;
    }

    public void setCurrentCalls(HashMap<String, Call> currentCalls) {
        this.currentCalls = currentCalls;
    }

    public HashMap<String, Call> getBlockedCalls() {
        return blockedCalls;
    }

    public void setBlockedCalls(HashMap<String, Call> blockedCalls) {
        this.blockedCalls = blockedCalls;
    }

    public HashMap<String, Call> getDroppedCalls() {
        return droppedCalls;
    }

    public void setDroppedCalls(HashMap<String, Call> droppedCalls) {
        this.droppedCalls = droppedCalls;
    }

    public HashMap<String, Call> getDelayedCalls() {
        return delayedCalls;
    }

    public void setDelayedCalls(HashMap<String, Call> delayedCalls) {
        this.delayedCalls = delayedCalls;
    }

    public RSA getSimulationRSA() {
        return simulationRSA;
    }

    public void setSimulationRSA(RSA simulationRSA) {
        this.simulationRSA = simulationRSA;
    }

}
