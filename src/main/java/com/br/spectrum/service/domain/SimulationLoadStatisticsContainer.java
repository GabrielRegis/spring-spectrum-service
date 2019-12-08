package com.br.spectrum.service.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class SimulationLoadStatisticsContainer {
    private HashMap<Integer, SimulationLoadStatistics> simulationLoadStatistics;

    public SimulationLoadStatisticsContainer(SimulationSummary simulationSummary) {
        this.simulationLoadStatistics = new HashMap<Integer, SimulationLoadStatistics>();
        simulationSummary.getSimulationInstanceSummaryHashMap().forEach((key, summary) -> {
            SimulationLoadStatistics loadStat = new SimulationLoadStatistics((int) summary.getSimulationInstanceSummary().getLoad(), new ArrayList(summary.getControlPlane().getSimulationStatistics().values()));
            this.simulationLoadStatistics.put(loadStat.getLoad(), loadStat);
        });
    }

    public HashMap<Integer, SimulationLoadStatistics> getSimulationLoadStatistics() {
        return simulationLoadStatistics;
    }

    public void setSimulationLoadStatistics(HashMap<Integer, SimulationLoadStatistics> simulationLoadStatistics) {
        this.simulationLoadStatistics = simulationLoadStatistics;
    }
}
