package com.br.spectrum.service.domain;

import com.br.spectrum.service.EventManagement.ControlPlane;
import com.br.spectrum.service.EventManagement.TopologySnapshot;

import java.util.UUID;


public class SimulationIterationStatistics {

    private String id;
    private double iterationTime;
    private TopologySnapshot topologySnapshot;

    public SimulationIterationStatistics(double iterationTime, ControlPlane controlPlane) {
        this.id = UUID.randomUUID().toString();
        this.iterationTime = iterationTime;
        TopologySnapshot topologySnapshot = new TopologySnapshot(controlPlane.getEonPhysicalTopology());
        this.topologySnapshot = topologySnapshot;
    }

    public TopologySnapshot getTopologySnapshot() {
        return topologySnapshot;
    }

    public void setTopologySnapshot(TopologySnapshot topologySnapshot) {
        this.topologySnapshot = topologySnapshot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getIterationTime() {
        return iterationTime;
    }

    public void setIterationTime(double iterationTime) {
        this.iterationTime = iterationTime;
    }
}
