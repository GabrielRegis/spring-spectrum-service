package com.br.spectrum.service.domain;

import com.br.spectrum.service.Utils.StatisticsUtils;

import java.util.ArrayList;

public class SimulationInstanceSummaryStatistics {

    private int totalAllocatedAmount;
    private int totalBlockedAmount;
    private int totalSuccessAmount;
    private int totalDelayedAmount;
    private int totalDroppedAmount;
    private int totalCallsAmount;
    private int totalBandwidthAmount;
    private int totalBlockedBandwidthAmount;

    private double totalAllocatedAmountStandardDeviation;
    private double totalBlockedAmountStandardDeviation;
    private double totalSuccessAmountStandardDeviation;
    private double totalDelayedAmountStandardDeviation;
    private double totalDroppedAmountStandardDeviation;
    private double totalCallsAmountStandardDeviation;
    private double blockProbabilityStandardDeviation;

    private double totalAllocatedAmountMean;
    private double totalBlockedAmountMean;
    private double totalSuccessAmountMean;
    private double totalDelayedAmountMean;
    private double totalDroppedAmountMean;
    private double totalCallsAmountMean;
    private double totalBandwidthAmountMean;
    private double totalBlockedBandwidthAmountMean;
    private double blockProbabilityMean;
    private double blockedBandWidthProbabilityMean;

    private double totalAllocatedAmountConfidenceInterval;
    private double totalBlockedAmountConfidenceInterval;
    private double totalSuccessAmountConfidenceInterval;
    private double totalDelayedAmountConfidenceInterval;
    private double totalDroppedAmountConfidenceInterval;
    private double totalCallsAmountConfidenceInterval;
    private double blockProbabilityConfidenceInterval;
    private double totalBlockedBandwidthConfidenceInterval;
    private double totalBandwidthConfidenceInterval;


    private ArrayList<Integer> allocatedAmountPerCycle;
    private ArrayList<Integer> blockedAmountPerCycle;
    private ArrayList<Integer> successAmountPerCycle;
    private ArrayList<Integer> delayedAmountPerCycle;
    private ArrayList<Integer> droppedAmountPerCycle;
    private ArrayList<Integer> totalAmountPerCycle;
    private ArrayList<Double> blockProbabilityPerCycle;
    private ArrayList<Integer> totalBandwidthAmountPerCycle;
    private ArrayList<Integer> totalBlockedBandwidthAmountPerCycle;
    private ArrayList<Double> bandwidthBlockProbabilityPerCycle;


    public SimulationInstanceSummaryStatistics() {
        this.allocatedAmountPerCycle = new ArrayList<>();
        this.blockedAmountPerCycle = new ArrayList<>();
        this.successAmountPerCycle = new ArrayList<>();
        this.delayedAmountPerCycle = new ArrayList<>();
        this.droppedAmountPerCycle = new ArrayList<>();
        this.totalAmountPerCycle = new ArrayList<>();
        this.blockProbabilityPerCycle = new ArrayList<>();
        this.totalBandwidthAmountPerCycle = new ArrayList<>();
        this.totalBlockedBandwidthAmountPerCycle = new ArrayList<>();
        this.bandwidthBlockProbabilityPerCycle = new ArrayList<>();
    }

    public void calculateStatistics(){
        this.totalCallsAmount = this.totalSuccessAmount + this.totalBlockedAmount;

        // Block Probability Calc
        int perCyclesAmount = this.getAllocatedAmountPerCycle().size();
        double[] blockProbabilityPerCycleArray = new double[perCyclesAmount];

        for(int i = 0; i< perCyclesAmount; i++){
            Integer[] blockedAmountArray = new Integer[blockedAmountPerCycle.size()];
            Integer[] totalAmountPerCycleArray = new Integer[totalAmountPerCycle.size()];
            this.blockedAmountPerCycle.toArray(blockedAmountArray);
            this.totalAmountPerCycle.toArray(totalAmountPerCycleArray);
            double blockedAmount = blockedAmountArray[i];
            double totalAmount = totalAmountPerCycleArray[i];
            double blockProbability = blockedAmount / totalAmount;
            blockProbabilityPerCycleArray[i] = blockProbability;
            this.blockProbabilityPerCycle.add(blockProbability);
        }

        this.blockProbabilityMean = StatisticsUtils.getMean(blockProbabilityPerCycleArray).getResult();
        this.blockProbabilityConfidenceInterval = Math.abs( this.blockProbabilityMean - StatisticsUtils.getConfidenceIntervalAbs(blockProbabilityPerCycleArray));

        // Blocked Bandwidth Probability Calc
        double[] blockedBandwidthProbabilityPerCycleArray = new double[perCyclesAmount];

        for(int i = 0; i< perCyclesAmount; i++){
            Integer[] blockedBandwidthAmountArray = new Integer[totalBlockedBandwidthAmountPerCycle.size()];
            Integer[] totalBandwidthAmountPerCycleArray = new Integer[totalBandwidthAmountPerCycle.size()];
            this.blockedAmountPerCycle.toArray(blockedBandwidthAmountArray);
            this.totalAmountPerCycle.toArray(totalBandwidthAmountPerCycleArray);
            double blockedBandwidthAmount = blockedBandwidthAmountArray[i];
            double totalBandwidthAmount = totalBandwidthAmountPerCycleArray[i];
            double blockedBandwidthProbability = blockedBandwidthAmount / totalBandwidthAmount;
            blockedBandwidthProbabilityPerCycleArray[i] = blockedBandwidthProbability;
            this.bandwidthBlockProbabilityPerCycle.add(blockedBandwidthProbability);
        }

        this.totalBlockedBandwidthAmountMean = StatisticsUtils.getMean(blockedBandwidthProbabilityPerCycleArray).getResult();
        this.totalBlockedBandwidthConfidenceInterval = StatisticsUtils.getConfidenceIntervalAbs(blockedBandwidthProbabilityPerCycleArray);

        // Means Calc
        this.totalAllocatedAmountMean = getPerCycleMean(totalAmountPerCycle);
        this.totalBlockedAmountMean = getPerCycleMean(blockedAmountPerCycle);
        this.totalSuccessAmountMean = getPerCycleMean(successAmountPerCycle);
        this.totalDroppedAmountMean = getPerCycleMean(droppedAmountPerCycle);
        this.totalDelayedAmountMean = getPerCycleMean(delayedAmountPerCycle);
        this.totalBandwidthAmountMean = getPerCycleMean(totalBandwidthAmountPerCycle);
        this.totalBlockedBandwidthAmountMean = getPerCycleMean(totalBlockedBandwidthAmountPerCycle);

        // Confidence Interval Calc
        this.totalAllocatedAmountConfidenceInterval = getPerCycleConfidenceInterval(totalAmountPerCycle);
        this.totalBlockedAmountConfidenceInterval = getPerCycleConfidenceInterval(blockedAmountPerCycle);
        this.totalSuccessAmountConfidenceInterval = getPerCycleConfidenceInterval(successAmountPerCycle);
        this.totalDroppedAmountConfidenceInterval = getPerCycleConfidenceInterval(droppedAmountPerCycle);
        this.totalDelayedAmountConfidenceInterval = getPerCycleConfidenceInterval(delayedAmountPerCycle);
        this.totalBandwidthConfidenceInterval = getPerCycleConfidenceInterval(totalBandwidthAmountPerCycle);
        this.totalBlockedBandwidthConfidenceInterval = getPerCycleConfidenceInterval(totalBlockedBandwidthAmountPerCycle);

    }


    public double getTotalBandwidthAmountMean() {
        return totalBandwidthAmountMean;
    }

    public void setTotalBandwidthAmountMean(double totalBandwidthAmountMean) {
        this.totalBandwidthAmountMean = totalBandwidthAmountMean;
    }

    public double getTotalBlockedBandwidthAmountMean() {
        return totalBlockedBandwidthAmountMean;
    }

    public void setTotalBlockedBandwidthAmountMean(double totalBlockedBandwidthAmountMean) {
        this.totalBlockedBandwidthAmountMean = totalBlockedBandwidthAmountMean;
    }

    public double getBlockedBandWidthProbabilityMean() {
        return blockedBandWidthProbabilityMean;
    }

    public void setBlockedBandWidthProbabilityMean(double blockedBandWidthProbabilityMean) {
        this.blockedBandWidthProbabilityMean = blockedBandWidthProbabilityMean;
    }

    public double getTotalBlockedBandwidthConfidenceInterval() {
        return totalBlockedBandwidthConfidenceInterval;
    }

    public void setTotalBlockedBandwidthConfidenceInterval(double totalBlockedBandwidthConfidenceInterval) {
        this.totalBlockedBandwidthConfidenceInterval = totalBlockedBandwidthConfidenceInterval;
    }

    public double getTotalBandwidthConfidenceInterval() {
        return totalBandwidthConfidenceInterval;
    }

    public void setTotalBandwidthConfidenceInterval(double totalBandwidthConfidenceInterval) {
        this.totalBandwidthConfidenceInterval = totalBandwidthConfidenceInterval;
    }

    public double getPerCycleMean(ArrayList<Integer> list){
        Integer[] listArray = new Integer[list.size()];
        return  StatisticsUtils.getIntegerMean(list.toArray(listArray)).getResult();
    }
    public double getPerCycleConfidenceInterval(ArrayList<Integer> list){
        Integer[] listArray = new Integer[list.size()];
        return  StatisticsUtils.getConfidenceIntervalAbsInteger(list.toArray(listArray)).getResult();
    }

    public int getTotalBandwidthAmount() {
        return totalBandwidthAmount;
    }

    public void setTotalBandwidthAmount(int totalBandwidthAmount) {
        this.totalBandwidthAmount = totalBandwidthAmount;
    }

    public int getTotalBlockedBandwidthAmount() {
        return totalBlockedBandwidthAmount;
    }

    public void setTotalBlockedBandwidthAmount(int totalBlockedBandwidthAmount) {
        this.totalBlockedBandwidthAmount = totalBlockedBandwidthAmount;
    }

    public ArrayList<Integer> getTotalBandwidthAmountPerCycle() {
        return totalBandwidthAmountPerCycle;
    }

    public void setTotalBandwidthAmountPerCycle(ArrayList<Integer> totalBandwidthAmountPerCycle) {
        this.totalBandwidthAmountPerCycle = totalBandwidthAmountPerCycle;
    }

    public ArrayList<Integer> getTotalBlockedBandwidthAmountPerCycle() {
        return totalBlockedBandwidthAmountPerCycle;
    }

    public void setTotalBlockedBandwidthAmountPerCycle(ArrayList<Integer> totalBlockedBandwidthAmountPerCycle) {
        this.totalBlockedBandwidthAmountPerCycle = totalBlockedBandwidthAmountPerCycle;
    }

    public ArrayList<Double> getBandwidthBlockProbabilityPerCycle() {
        return bandwidthBlockProbabilityPerCycle;
    }

    public void setBandwidthBlockProbabilityPerCycle(ArrayList<Double> bandwidthBlockProbabilityPerCycle) {
        this.bandwidthBlockProbabilityPerCycle = bandwidthBlockProbabilityPerCycle;
    }

    public ArrayList<Double> getBlockProbabilityPerCycle() {
        return blockProbabilityPerCycle;
    }

    public void setBlockProbabilityPerCycle(ArrayList<Double> blockProbabilityPerCycle) {
        this.blockProbabilityPerCycle = blockProbabilityPerCycle;
    }

    public ArrayList<Integer> getTotalAmountPerCycle() {
        return totalAmountPerCycle;
    }

    public void setTotalAmountPerCycle(ArrayList<Integer> totalAmountPerCycle) {
        this.totalAmountPerCycle = totalAmountPerCycle;
    }

    public ArrayList<Integer> getAllocatedAmountPerCycle() {
        return allocatedAmountPerCycle;
    }

    public void setAllocatedAmountPerCycle(ArrayList<Integer> allocatedAmountPerCycle) {
        this.allocatedAmountPerCycle = allocatedAmountPerCycle;
    }

    public ArrayList<Integer> getBlockedAmountPerCycle() {
        return blockedAmountPerCycle;
    }

    public void setBlockedAmountPerCycle(ArrayList<Integer> blockedAmountPerCycle) {
        this.blockedAmountPerCycle = blockedAmountPerCycle;
    }

    public ArrayList<Integer> getSuccessAmountPerCycle() {
        return successAmountPerCycle;
    }

    public void setSuccessAmountPerCycle(ArrayList<Integer> successAmountPerCycle) {
        this.successAmountPerCycle = successAmountPerCycle;
    }

    public ArrayList<Integer> getDelayedAmountPerCycle() {
        return delayedAmountPerCycle;
    }

    public void setDelayedAmountPerCycle(ArrayList<Integer> delayedAmountPerCycle) {
        this.delayedAmountPerCycle = delayedAmountPerCycle;
    }

    public ArrayList<Integer> getDroppedAmountPerCycle() {
        return droppedAmountPerCycle;
    }

    public void setDroppedAmountPerCycle(ArrayList<Integer> droppedAmountPerCycle) {
        this.droppedAmountPerCycle = droppedAmountPerCycle;
    }


    public double getBlockProbabilityStandardDeviation() {
        return blockProbabilityStandardDeviation;
    }

    public void setBlockProbabilityStandardDeviation(double blockProbabilityStandardDeviation) {
        this.blockProbabilityStandardDeviation = blockProbabilityStandardDeviation;
    }

    public double getBlockProbabilityMean() {
        return blockProbabilityMean;
    }

    public void setBlockProbabilityMean(double blockProbabilityMean) {
        this.blockProbabilityMean = blockProbabilityMean;
    }

    public double getBlockProbabilityConfidenceInterval() {
        return blockProbabilityConfidenceInterval;
    }

    public void setBlockProbabilityConfidenceInterval(double blockProbabilityConfidenceInterval) {
        this.blockProbabilityConfidenceInterval = blockProbabilityConfidenceInterval;
    }

    public double getTotalAllocatedAmountStandardDeviation() {
        return totalAllocatedAmountStandardDeviation;
    }

    public void setTotalAllocatedAmountStandardDeviation(double totalAllocatedAmountStandardDeviation) {
        this.totalAllocatedAmountStandardDeviation = totalAllocatedAmountStandardDeviation;
    }

    public double getTotalBlockedAmountStandardDeviation() {
        return totalBlockedAmountStandardDeviation;
    }

    public void setTotalBlockedAmountStandardDeviation(double totalBlockedAmountStandardDeviation) {
        this.totalBlockedAmountStandardDeviation = totalBlockedAmountStandardDeviation;
    }

    public double getTotalSuccessAmountStandardDeviation() {
        return totalSuccessAmountStandardDeviation;
    }

    public void setTotalSuccessAmountStandardDeviation(double totalSuccessAmountStandardDeviation) {
        this.totalSuccessAmountStandardDeviation = totalSuccessAmountStandardDeviation;
    }

    public double getTotalDelayedAmountStandardDeviation() {
        return totalDelayedAmountStandardDeviation;
    }

    public void setTotalDelayedAmountStandardDeviation(double totalDelayedAmountStandardDeviation) {
        this.totalDelayedAmountStandardDeviation = totalDelayedAmountStandardDeviation;
    }

    public double getTotalDroppedAmountStandardDeviation() {
        return totalDroppedAmountStandardDeviation;
    }

    public void setTotalDroppedAmountStandardDeviation(double totalDroppedAmountStandardDeviation) {
        this.totalDroppedAmountStandardDeviation = totalDroppedAmountStandardDeviation;
    }

    public double getTotalCallsAmountStandardDeviation() {
        return totalCallsAmountStandardDeviation;
    }

    public void setTotalCallsAmountStandardDeviation(double totalCallsAmountStandardDeviation) {
        this.totalCallsAmountStandardDeviation = totalCallsAmountStandardDeviation;
    }

    public double getTotalAllocatedAmountMean() {
        return totalAllocatedAmountMean;
    }

    public void setTotalAllocatedAmountMean(double totalAllocatedAmountMean) {
        this.totalAllocatedAmountMean = totalAllocatedAmountMean;
    }

    public double getTotalBlockedAmountMean() {
        return totalBlockedAmountMean;
    }

    public void setTotalBlockedAmountMean(double totalBlockedAmountMean) {
        this.totalBlockedAmountMean = totalBlockedAmountMean;
    }

    public double getTotalSuccessAmountMean() {
        return totalSuccessAmountMean;
    }

    public void setTotalSuccessAmountMean(double totalSuccessAmountMean) {
        this.totalSuccessAmountMean = totalSuccessAmountMean;
    }

    public double getTotalDelayedAmountMean() {
        return totalDelayedAmountMean;
    }

    public void setTotalDelayedAmountMean(double totalDelayedAmountMean) {
        this.totalDelayedAmountMean = totalDelayedAmountMean;
    }

    public double getTotalDroppedAmountMean() {
        return totalDroppedAmountMean;
    }

    public void setTotalDroppedAmountMean(double totalDroppedAmountMean) {
        this.totalDroppedAmountMean = totalDroppedAmountMean;
    }

    public double getTotalCallsAmountMean() {
        return totalCallsAmountMean;
    }

    public void setTotalCallsAmountMean(double totalCallsAmountMean) {
        this.totalCallsAmountMean = totalCallsAmountMean;
    }


    public double getTotalAllocatedAmountConfidenceInterval() {
        return totalAllocatedAmountConfidenceInterval;
    }

    public void setTotalAllocatedAmountConfidenceInterval(double totalAllocatedAmountConfidenceInterval) {
        this.totalAllocatedAmountConfidenceInterval = totalAllocatedAmountConfidenceInterval;
    }

    public double getTotalBlockedAmountConfidenceInterval() {
        return totalBlockedAmountConfidenceInterval;
    }

    public void setTotalBlockedAmountConfidenceInterval(double totalBlockedAmountConfidenceInterval) {
        this.totalBlockedAmountConfidenceInterval = totalBlockedAmountConfidenceInterval;
    }

    public double getTotalSuccessAmountConfidenceInterval() {
        return totalSuccessAmountConfidenceInterval;
    }

    public void setTotalSuccessAmountConfidenceInterval(double totalSuccessAmountConfidenceInterval) {
        this.totalSuccessAmountConfidenceInterval = totalSuccessAmountConfidenceInterval;
    }

    public double getTotalDelayedAmountConfidenceInterval() {
        return totalDelayedAmountConfidenceInterval;
    }

    public void setTotalDelayedAmountConfidenceInterval(double totalDelayedAmountConfidenceInterval) {
        this.totalDelayedAmountConfidenceInterval = totalDelayedAmountConfidenceInterval;
    }

    public double getTotalDroppedAmountConfidenceInterval() {
        return totalDroppedAmountConfidenceInterval;
    }

    public void setTotalDroppedAmountConfidenceInterval(double totalDroppedAmountConfidenceInterval) {
        this.totalDroppedAmountConfidenceInterval = totalDroppedAmountConfidenceInterval;
    }

    public double getTotalCallsAmountConfidenceInterval() {
        return totalCallsAmountConfidenceInterval;
    }

    public void setTotalCallsAmountConfidenceInterval(double totalCallsAmountConfidenceInterval) {
        this.totalCallsAmountConfidenceInterval = totalCallsAmountConfidenceInterval;
    }



    public int getTotalAllocatedAmount() {
        return totalAllocatedAmount;
    }

    public void setTotalAllocatedAmount(int totalAllocatedAmount) {
        this.totalAllocatedAmount = totalAllocatedAmount;
    }

    public int getTotalBlockedAmount() {
        return totalBlockedAmount;
    }

    public void setTotalBlockedAmount(int totalBlockedAmount) {
        this.totalBlockedAmount = totalBlockedAmount;
    }

    public int getTotalSuccessAmount() {
        return totalSuccessAmount;
    }

    public void setTotalSuccessAmount(int totalSuccessAmount) {
        this.totalSuccessAmount = totalSuccessAmount;
    }

    public int getTotalDelayedAmount() {
        return totalDelayedAmount;
    }

    public void setTotalDelayedAmount(int totalDelayedAmount) {
        this.totalDelayedAmount = totalDelayedAmount;
    }

    public int getTotalDroppedAmount() {
        return totalDroppedAmount;
    }

    public void setTotalDroppedAmount(int totalDroppedAmount) {
        this.totalDroppedAmount = totalDroppedAmount;
    }

    public int getTotalCallsAmount() {
        return totalCallsAmount;
    }

    public void setTotalCallsAmount(int totalCallsAmount) {
        this.totalCallsAmount = totalCallsAmount;
    }
}
