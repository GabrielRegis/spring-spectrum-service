package com.br.spectrum.service.domain;

public class CallClassGeneralStatistics {
    private String className;
    private String classId;
    private String color;
    private int blockedAmount;
    private double blockedProbability;

    public CallClassGeneralStatistics(String className, String classId, String color, int blockedAmount) {
        this.className = className;
        this.classId = classId;
        this.color = color;
        this.blockedAmount = blockedAmount;
    }

    public CallClassGeneralStatistics(String className, String classId, String color, double blockedProbability) {
        this.className = className;
        this.classId = classId;
        this.color = color;
        this.blockedProbability = blockedProbability;
    }

    public double getBlockedProbability() {
        return blockedProbability;
    }

    public void setBlockedProbability(double blockedProbability) {
        this.blockedProbability = blockedProbability;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBlockedAmount() {
        return blockedAmount;
    }

    public void setBlockedAmount(int blockedAmount) {
        this.blockedAmount = blockedAmount;
    }
}
