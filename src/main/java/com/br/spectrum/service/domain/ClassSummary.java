package com.br.spectrum.service.domain;

import com.br.spectrum.service.EventManagement.CallClass;
import com.br.spectrum.service.EventManagement.ControlPlane;

import java.util.UUID;

public class ClassSummary {

    private String id;
    private int cycleNum;
    private String classId;
    private String className;
    private SimulationInstanceSummaryStatistics statistics;

    public ClassSummary(CallClass callClass, ControlPlane controlPlane) {
        this.id = UUID.randomUUID().toString();
        this.classId = callClass.getId();
        this.className = callClass.getClassName();
        this.statistics = new SimulationInstanceSummaryStatistics();
        this.cycleNum = 0;
        this.addCycle(controlPlane);
    }

    public Void addCycle(ControlPlane controlPlane){

        int[] totalAllocatedAmount = new int[1];
        int[] totalBlockedAmount = new int[1];
        int[] totalSuccessAmount = new int[1];
        int[] totalDelayedAmount = new int[1];
        int[] totalDroppedAmount = new int[1];
        int totalCallsAmount = 0;
        int[] totalBlockedBandwidthAmount = new int[1];
        int[] totalSuccessBandwidthAmount = new int[1];

        this.cycleNum++;
        controlPlane.getAllocatedCalls().forEach((key, call) ->{
            if(call.getCallClass().getId() == this.getClassId()){
//                this.statistics.setTotalAllocatedAmount(this.statistics.getTotalAllocatedAmount() + 1);
                totalAllocatedAmount[0] += 1;
            }
        });

        controlPlane.getBlockedCalls().forEach((key, call) ->{
            if(call.getCallClass().getId() == this.getClassId()){
//                this.statistics.setTotalBlockedAmount(this.statistics.getTotalBlockedAmount() + 1);
//                this.statistics.setTotalBlockedBandwidthAmount(this.statistics.getTotalBlockedBandwidthAmount() + call.getCallClass().getRequiredBandwidth());
                totalBlockedAmount[0] += 1;
                totalBlockedBandwidthAmount[0] += call.getCallClass().getRequiredBandwidth();
            }
        });

        controlPlane.getSuccessfulCalls().forEach((key, call) ->{
            if(call.getCallClass().getId() == this.getClassId()){
//                this.statistics.setTotalSuccessAmount(this.statistics.getTotalSuccessAmount() + 1);
                totalSuccessAmount[0] += 1;
                totalSuccessBandwidthAmount[0] += call.getCallClass().getRequiredBandwidth();
            }
        });

        controlPlane.getDelayedCalls().forEach((key, call) ->{
            if(call.getCallClass().getId() == this.getClassId()){
//                this.statistics.setTotalDelayedAmount(this.statistics.getTotalDelayedAmount() + 1);
                totalDelayedAmount[0] += 1;
            }
        });

        controlPlane.getDroppedCalls().forEach((key, call) ->{
            if(call.getCallClass().getId() == this.getClassId()){
//                this.statistics.setTotalDroppedAmount(this.statistics.getTotalDroppedAmount() + 1);
                totalDroppedAmount[0] += 1;
            }
        });
        totalCallsAmount = totalSuccessAmount[0] + totalBlockedAmount[0];

        this.statistics.setTotalAllocatedAmount(this.statistics.getTotalAllocatedAmount() + totalAllocatedAmount[0]);
        this.statistics.setTotalBlockedAmount(this.statistics.getTotalBlockedAmount() + totalBlockedAmount[0]);
        this.statistics.setTotalBlockedBandwidthAmount(this.statistics.getTotalBlockedBandwidthAmount() + totalBlockedBandwidthAmount[0]);
        this.statistics.setTotalSuccessAmount(this.statistics.getTotalSuccessAmount() + totalSuccessAmount[0]);
        this.statistics.setTotalDelayedAmount(this.statistics.getTotalDelayedAmount() + totalDelayedAmount[0]);
        this.statistics.setTotalDroppedAmount(this.statistics.getTotalDroppedAmount() + totalDroppedAmount[0]);
        this.statistics.setTotalBandwidthAmount(this.statistics.getTotalBandwidthAmount() + totalBlockedBandwidthAmount[0] + totalSuccessBandwidthAmount[0]);
        this.statistics.setTotalCallsAmount(this.statistics.getTotalCallsAmount() + totalCallsAmount);

        this.statistics.getAllocatedAmountPerCycle().add(totalAllocatedAmount[0]);
        this.statistics.getBlockedAmountPerCycle().add(totalBlockedAmount[0]);
        this.statistics.getSuccessAmountPerCycle().add(totalSuccessAmount[0]);
        this.statistics.getDroppedAmountPerCycle().add(totalDroppedAmount[0]);
        this.statistics.getDelayedAmountPerCycle().add(totalDelayedAmount[0]);
        this.statistics.getTotalAmountPerCycle().add(totalCallsAmount);
        this.statistics.getTotalBlockedBandwidthAmountPerCycle().add(totalBlockedBandwidthAmount[0]);
        this.statistics.getTotalBandwidthAmountPerCycle().add(totalBlockedBandwidthAmount[0] + totalSuccessBandwidthAmount[0]);
        return null;
    }



    public int getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(int cycleNum) {
        this.cycleNum = cycleNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public SimulationInstanceSummaryStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(SimulationInstanceSummaryStatistics statistics) {
        this.statistics = statistics;
    }
}
