package com.br.spectrum.service.domain;

import com.br.spectrum.service.EventManagement.CallClass;
import com.br.spectrum.service.EventManagement.ControlPlane;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimulationInstanceSummary {

    private String id;
    private int load;
    private int cycleNum;
    private List<ClassSummary> classSummaries;
    private SimulationSummary simulationSummary;
    private SimulationInstanceSummaryStatistics statistics;

    public SimulationInstanceSummary(double load, ControlPlane controlPlane, ArrayList<CallClass> callClasses) {
        this.id = UUID.randomUUID().toString();
        this.load = (int) load;
        this.cycleNum = 1;
        this.classSummaries = new ArrayList<ClassSummary>();
        callClasses.forEach(callClass -> {
            ClassSummary classSummary = new ClassSummary(callClass, controlPlane);
            this.classSummaries.add(classSummary);
        });
        this.statistics = new SimulationInstanceSummaryStatistics();
    }

    public int getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(int cycleNum) {
        this.cycleNum = cycleNum;
    }

    public void setClassSummaries(List<ClassSummary> classSummaries) {
        this.classSummaries = classSummaries;
    }

    public SimulationSummary getSimulationSummary() {
        return simulationSummary;
    }

    public void setSimulationSummary(SimulationSummary simulationSummary) {
        this.simulationSummary = simulationSummary;
    }

    public Void addCycle(ControlPlane controlPlane){
        this.classSummaries.forEach(classSummary  -> {
            classSummary.addCycle(controlPlane);

        });
        this.cycleNum ++;
        return null;
    }

    public void calculateInfos(){

        this.classSummaries.forEach(classSummary -> {
            this.statistics.setTotalAllocatedAmount(this.statistics.getTotalAllocatedAmount() + classSummary.getStatistics().getTotalAllocatedAmount());
            this.statistics.setTotalBlockedAmount(this.statistics.getTotalBlockedAmount() + classSummary.getStatistics().getTotalBlockedAmount());
            this.statistics.setTotalDelayedAmount(this.statistics.getTotalDelayedAmount() + classSummary.getStatistics().getTotalDelayedAmount());
            this.statistics.setTotalDroppedAmount(this.statistics.getTotalDroppedAmount() + classSummary.getStatistics().getTotalDroppedAmount());
            this.statistics.setTotalSuccessAmount(this.statistics.getTotalSuccessAmount() + classSummary.getStatistics().getTotalSuccessAmount());

            classSummary.getStatistics().calculateStatistics();
        });

        for (int i = 0; i< cycleNum; i++){
            final int finalIndex = i;
            int cycleTotalAllocatedAmount = 0;
            int cycleTotalBlockedAmount = 0;
            int cycleTotalDroppedAmount = 0;
            int cycleTotalDelayedAmount = 0;
            int cycleTotalSuccessAmount = 0;
            int cycleTotalBandwidthAmount = 0;
            int cycleTotalBlockedBandwidthAmount =0;
            int cycleTotalCallsAmount =0;

            for(ClassSummary classSummary : this.classSummaries){
                cycleTotalAllocatedAmount+=(int)getIndexValueFromArrayList(classSummary.getStatistics().getAllocatedAmountPerCycle(), finalIndex);
                cycleTotalBlockedAmount+=(int)getIndexValueFromArrayList(classSummary.getStatistics().getBlockedAmountPerCycle(), finalIndex);
                cycleTotalDroppedAmount+=(int)getIndexValueFromArrayList(classSummary.getStatistics().getDroppedAmountPerCycle(), finalIndex);
                cycleTotalDelayedAmount+=(int)getIndexValueFromArrayList(classSummary.getStatistics().getDelayedAmountPerCycle(), finalIndex);
                cycleTotalSuccessAmount+=(int)getIndexValueFromArrayList(classSummary.getStatistics().getSuccessAmountPerCycle(), finalIndex);
                cycleTotalBandwidthAmount+=getIndexValueFromArrayList(classSummary.getStatistics().getTotalBandwidthAmountPerCycle(), finalIndex);
                cycleTotalBlockedBandwidthAmount+=getIndexValueFromArrayList(classSummary.getStatistics().getTotalBlockedBandwidthAmountPerCycle(), finalIndex);
                cycleTotalCallsAmount+=(int)getIndexValueFromArrayList(classSummary.getStatistics().getSuccessAmountPerCycle(), finalIndex) + (int)getIndexValueFromArrayList(classSummary.getStatistics().getBlockedAmountPerCycle(), finalIndex);
            }
            this.statistics.getAllocatedAmountPerCycle().add(cycleTotalAllocatedAmount);
            this.statistics.getBlockedAmountPerCycle().add(cycleTotalBlockedAmount);
            this.statistics.getDelayedAmountPerCycle().add(cycleTotalDelayedAmount);
            this.statistics.getDroppedAmountPerCycle().add(cycleTotalDroppedAmount);
            this.statistics.getSuccessAmountPerCycle().add(cycleTotalSuccessAmount);
            this.statistics.getTotalAmountPerCycle().add(cycleTotalCallsAmount);
            this.statistics.getTotalBandwidthAmountPerCycle().add(cycleTotalBandwidthAmount);
            this.statistics.getTotalBlockedBandwidthAmountPerCycle().add(cycleTotalBlockedBandwidthAmount);
        }
        this.statistics.calculateStatistics();
    }

    public double getIndexValueFromArrayList(ArrayList<Integer> list, int index){
        Integer[] listArray = new Integer[list.size()];
        list.toArray(listArray);
        return listArray[index];
    }

    public SimulationInstanceSummaryStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(SimulationInstanceSummaryStatistics statistics) {
        this.statistics = statistics;
    }


    public List<ClassSummary> getClassSummaries() {
        return classSummaries;
    }

    public void setClassSummaries(ArrayList<ClassSummary> classSummaries) {
        this.classSummaries = classSummaries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
