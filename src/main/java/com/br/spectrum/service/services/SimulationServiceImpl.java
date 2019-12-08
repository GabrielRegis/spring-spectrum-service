package com.br.spectrum.service.services;

import com.br.spectrum.service.EventManagement.SimulationAgent;
import com.br.spectrum.service.SharedLayer.Models.SimulationConfiguration;
import com.br.spectrum.service.domain.SimulationSummary;
import com.br.spectrum.service.domain.SimulationSummaryDAO;
import org.springframework.stereotype.Service;

@Service
public class SimulationServiceImpl implements SimulationService {


    @Override
    public SimulationSummaryDAO runSimulation(SimulationConfiguration simulationConfiguration) {
        SimulationAgent simulationAgent = new SimulationAgent(simulationConfiguration, this);
        SimulationSummary simulationSummary = simulationAgent.runSimulation();
        SimulationSummaryDAO simulationSummaryDAO = new SimulationSummaryDAO(simulationSummary);
        return simulationSummaryDAO;
    }

}
