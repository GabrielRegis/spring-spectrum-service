package com.br.spectrum.service.SharedLayer.Models;

import java.util.ArrayList;

public class ClassesConfiguration {
    private int callsNumber;
    private ArrayList<CallClassConfiguration> flowClasses;

    public ClassesConfiguration(){

    }

    public ClassesConfiguration(int callsNumber, ArrayList<CallClassConfiguration> flowClasses) {
        this.callsNumber = callsNumber;
        this.flowClasses = flowClasses;
    }

    public int getCallsNumber() {
        return callsNumber;
    }

    public void setCallsNumber(int callsNumber) {
        this.callsNumber = callsNumber;
    }

    public ArrayList<CallClassConfiguration> getFlowClassConfigurations() {
        return flowClasses;
    }

    public void setFlowClassConfigurations(ArrayList<CallClassConfiguration> flowClassConfigurations) {
        this.flowClasses = flowClassConfigurations;
    }

    public ArrayList<CallClassConfiguration> getFlowClasses() {
        return flowClasses;
    }

    public void setFlowClasses(ArrayList<CallClassConfiguration> flowClasses) {
        this.flowClasses = flowClasses;
    }
}
