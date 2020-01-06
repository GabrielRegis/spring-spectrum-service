package com.br.spectrum.service.EventManagement;


import com.br.spectrum.service.PhysicalLayer.EONNode;
import com.br.spectrum.service.PhysicalLayer.EONPhysicalTopology;
import com.br.spectrum.service.SharedLayer.Models.CallClassConfiguration;
import com.br.spectrum.service.SharedLayer.Models.SimulationConfiguration;
import com.br.spectrum.service.SharedLayer.Models.TopologyConfiguration;
import com.br.spectrum.service.domain.SimulationInstanceSummary;
import com.br.spectrum.service.domain.SimulationSummary;
import com.br.spectrum.service.services.SimulationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationAgent {
    private SimulationConfiguration simulationConfiguration;
    private ArrayList<SimulationInstance> simulationInstances;
    private ArrayList<Integer> seeds;
    private ArrayList<CallClass> callClasses;
    public static SimulationSummary simulationSummary = new SimulationSummary();


    public SimulationAgent(SimulationConfiguration simulationConfiguration, SimulationService service) {
        this.simulationConfiguration = simulationConfiguration;

        HashMap<String, EONNode> nodesMap = new HashMap<String, EONNode>();

        TopologyConfiguration topologyConfiguration = simulationConfiguration.getTopologyConfiguration();
        EONPhysicalTopology eonPhysicalTopology = new EONPhysicalTopology(topologyConfiguration.getNodes(), topologyConfiguration.getLinks());


        this.callClasses = new ArrayList<CallClass>();
        for(CallClassConfiguration classConfig : simulationConfiguration.getClassesConfiguration().getFlowClassConfigurations()){
            int requiredBandwidth = ThreadLocalRandom.current().nextInt(classConfig.getMinBandwidth(), classConfig.getMaxBandwidth() + 1);
            int holdingTime = ThreadLocalRandom.current().nextInt(classConfig.getMinHoldingTime(), classConfig.getMaxHoldingTime() + 1);
            CallClass callClass = new CallClass(classConfig.getName(), classConfig.getFrequency(), requiredBandwidth, holdingTime, classConfig.getDegradationConfiguration(), classConfig.getColor());
            this.callClasses.add(callClass);
        }

        this.seeds = new ArrayList<Integer>();
        for(int i = 1; i<simulationConfiguration.getGeneralConfigurations().getSimulationCycles(); i++){
            int seed = (int) ThreadLocalRandom.current().nextLong();
            this.seeds.add(seed);
        }
        this.simulationInstances = new ArrayList<SimulationInstance>();
        for(int seed:this.seeds){
            int minLoad = simulationConfiguration.getGeneralConfigurations().getMinLoad();
            int maxLoad = simulationConfiguration.getGeneralConfigurations().getMaxLoad();
            int loadStep = simulationConfiguration.getGeneralConfigurations().getLoadStep();


            for(int load = minLoad; load<=maxLoad; load+=loadStep){
                SimulationInstance simulationInstance = new SimulationInstance(simulationConfiguration.getClassesConfiguration().getCallsNumber(),load, seed, this.callClasses, eonPhysicalTopology, service);
                this.simulationInstances.add(simulationInstance);
            }
        }
    }

    public SimulationSummary runSimulation(){

        for(SimulationInstance simulationInstance : this.getSimulationInstances()){
            SimulationInstanceSummaryWithControlPlane simulationInstanceSummaryWithControlPlane =  simulationInstance.runSimulationInstance();

            // Checa se existem sumários de simulação com a mesma carga, em ciclos anteriores
            if( SimulationAgent.simulationSummary.getSimulationInstanceSummaryHashMap().containsKey((int)simulationInstanceSummaryWithControlPlane.getSimulationInstanceSummary().getLoad())){
                SimulationInstanceSummary existingSimulationInstanceSummary = SimulationAgent.simulationSummary.getSimulationInstanceSummaryHashMap().get((int)simulationInstanceSummaryWithControlPlane.getSimulationInstanceSummary().getLoad()).getSimulationInstanceSummary();
                existingSimulationInstanceSummary.addCycle(simulationInstanceSummaryWithControlPlane.getControlPlane());
                SimulationAgent.simulationSummary.getSimulationInstanceSummaryHashMap().get((int)existingSimulationInstanceSummary.getLoad()).setSimulationInstanceSummary(existingSimulationInstanceSummary);
                continue;
            }

//            simulationInstanceSummaryWithControlPlane.getSimulationInstanceSummary().calculateInfos();
            simulationInstanceSummaryWithControlPlane.getSimulationInstanceSummary().addCycle(simulationInstanceSummaryWithControlPlane.getControlPlane());

            SimulationAgent.simulationSummary.getSimulationInstanceSummaryHashMap().put((int)simulationInstanceSummaryWithControlPlane.getSimulationInstanceSummary().getLoad(), simulationInstanceSummaryWithControlPlane);
        }

        SimulationSummary finalSummary = SimulationAgent.simulationSummary;
        SimulationAgent.simulationSummary = new SimulationSummary();

        return finalSummary;
    }

    public SimulationConfiguration getSimulationConfiguration() {
        return simulationConfiguration;
    }

    public void setSimulationConfiguration(SimulationConfiguration simulationConfiguration) {
        this.simulationConfiguration = simulationConfiguration;
    }

    public ArrayList<SimulationInstance> getSimulationInstances() {
        return simulationInstances;
    }

    public void setSimulationInstances(ArrayList<SimulationInstance> simulationInstances) {
        this.simulationInstances = simulationInstances;
    }

    public ArrayList<Integer> getSeeds() {
        return seeds;
    }

    public void setSeeds(ArrayList<Integer> seeds) {
        this.seeds = seeds;
    }

    public ArrayList<CallClass> getCallClasses() {
        return callClasses;
    }

    public void setCallClasses(ArrayList<CallClass> callClasses) {
        this.callClasses = callClasses;
    }
}
