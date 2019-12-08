package com.br.spectrum.service.SharedLayer.Models;

public class SimulationConfiguration {
    private GeneralConfigurations generalConfigurations;
    private ClassesConfiguration classesConfiguration;
    private TopologyConfiguration topologyConfiguration;

    public SimulationConfiguration(){

    }

    public SimulationConfiguration(GeneralConfigurations generalConfigurations, ClassesConfiguration classesConfiguration, TopologyConfiguration topologyConfiguration) {
        this.generalConfigurations = generalConfigurations;
        this.classesConfiguration = classesConfiguration;
        this.topologyConfiguration = topologyConfiguration;
    }

    public GeneralConfigurations getGeneralConfigurations() {
        return generalConfigurations;
    }

    public void setGeneralConfigurations(GeneralConfigurations generalConfigurations) {
        this.generalConfigurations = generalConfigurations;
    }

    public ClassesConfiguration getClassesConfiguration() {
        return classesConfiguration;
    }

    public void setClassesConfiguration(ClassesConfiguration classesConfiguration) {
        this.classesConfiguration = classesConfiguration;
    }

    public TopologyConfiguration getTopologyConfiguration() {
        return topologyConfiguration;
    }

    public void setTopologyConfiguration(TopologyConfiguration topologyConfiguration) {
        this.topologyConfiguration = topologyConfiguration;
    }
}
