package com.br.spectrum.service.domain;

import com.br.spectrum.service.EventManagement.SimulationInstanceSummaryWithControlPlane;

import java.util.HashMap;
import java.util.UUID;

public class SimulationSummary {

    private String id;
    private String simulationId;

    private transient HashMap<Integer, SimulationInstanceSummaryWithControlPlane> simulationInstanceSummaryHashMap;

    public SimulationSummary() {
        this.id = UUID.randomUUID().toString();
        this.simulationId = UUID.randomUUID().toString();
        this.simulationInstanceSummaryHashMap = new HashMap<>();
    }

    public HashMap<Integer, SimulationInstanceSummaryWithControlPlane> getSimulationInstanceSummaryHashMap() {
        return simulationInstanceSummaryHashMap;
    }

    public void setSimulationInstanceSummaryHashMap(HashMap<Integer, SimulationInstanceSummaryWithControlPlane> simulationInstanceSummaryHashMap) {
        this.simulationInstanceSummaryHashMap = simulationInstanceSummaryHashMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSimulationId() {
        return simulationId;
    }

    public void setSimulationId(String simulationId) {
        this.simulationId = simulationId;
    }

}
