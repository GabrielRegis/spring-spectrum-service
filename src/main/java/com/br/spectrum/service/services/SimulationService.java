package com.br.spectrum.service.services;

import com.br.spectrum.service.SharedLayer.Models.SimulationConfiguration;
import com.br.spectrum.service.domain.SimulationSummaryDAO;

public interface SimulationService {
    SimulationSummaryDAO runSimulation(SimulationConfiguration simulationConfiguration);
}
