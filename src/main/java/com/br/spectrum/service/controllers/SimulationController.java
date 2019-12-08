package com.br.spectrum.service.controllers;

import com.br.spectrum.service.SharedLayer.Models.SimulationConfiguration;
import com.br.spectrum.service.domain.SimulationLoadStatisticsContainer;
import com.br.spectrum.service.domain.SimulationSummaryDAO;
import com.br.spectrum.service.services.SimulationService;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(SimulationController.BASE_URL)
public class SimulationController {
    public static final String BASE_URL = "/api/simulation";
    public static SocketIOServer server;
    public HashMap<String, SimulationLoadStatisticsContainer> simulationLoadStatisticsContainerHashMap = new HashMap<>();
    private final SimulationService simulationIterationStatisticsService;

    public SimulationController(SimulationService simulationIterationStatisticsService) {
        this.simulationIterationStatisticsService = simulationIterationStatisticsService;
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(4113);
        server = new SocketIOServer(config);
    }

    @PostMapping
    @CrossOrigin
    public SimulationSummaryDAO runSimulation(@Valid @RequestBody SimulationConfiguration configuration) {
        SimulationSummaryDAO summaryDAO = simulationIterationStatisticsService.runSimulation(configuration);
        summaryDAO.setStartLoad(configuration.getGeneralConfigurations().getMinLoad());
        summaryDAO.setLoadStep(configuration.getGeneralConfigurations().getLoadStep());
        return summaryDAO;
    }

}
