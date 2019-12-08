package com.br.spectrum.service.EventManagement;

import com.br.spectrum.service.domain.SimulationInstanceSummary;

public class SimulationInstanceSummaryWithControlPlane {

    private ControlPlane controlPlane;
    private SimulationInstanceSummary simulationInstanceSummary;

    public SimulationInstanceSummaryWithControlPlane(ControlPlane controlPlane, SimulationInstanceSummary simulationInstanceSummary) {
        this.controlPlane = controlPlane;
        this.simulationInstanceSummary = simulationInstanceSummary;
    }

    public ControlPlane getControlPlane() {
        return controlPlane;
    }

    public void setControlPlane(ControlPlane controlPlane) {
        this.controlPlane = controlPlane;
    }

    public SimulationInstanceSummary getSimulationInstanceSummary() {
        return simulationInstanceSummary;
    }

    public void setSimulationInstanceSummary(SimulationInstanceSummary simulationInstanceSummary) {
        this.simulationInstanceSummary = simulationInstanceSummary;
    }
}
