package com.br.spectrum.service.SharedLayer.Models;

public class GeneralConfigurations {
    private int simulationCycles;
    private int minLoad;
    private int maxLoad;
    private int loadStep;

    public GeneralConfigurations(){

    }

    public GeneralConfigurations(int simulationCycles, int minLoad, int maxLoad, int loadStep) {
        this.simulationCycles = simulationCycles;
        this.minLoad = minLoad;
        this.maxLoad = maxLoad;
        this.loadStep = loadStep;
    }

    public int getSimulationCycles() {
        return simulationCycles;
    }

    public void setSimulationCycles(int simulationCycles) {
        this.simulationCycles = simulationCycles;
    }

    public int getMinLoad() {
        return minLoad;
    }

    public void setMinLoad(int minLoad) {
        this.minLoad = minLoad;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public int getLoadStep() {
        return loadStep;
    }

    public void setLoadStep(int loadStep) {
        this.loadStep = loadStep;
    }
}
