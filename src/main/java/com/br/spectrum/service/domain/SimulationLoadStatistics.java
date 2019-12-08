package com.br.spectrum.service.domain;

import java.util.ArrayList;

public class SimulationLoadStatistics {
    private int page;
    private int load;
    private ArrayList<SimulationIterationStatistics> iterationStatistics;

    public SimulationLoadStatistics(int load, ArrayList<SimulationIterationStatistics> iterationStatistics) {
        this.page = 0;
        this.load = load;
        this.iterationStatistics = iterationStatistics;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public ArrayList<SimulationIterationStatistics> getIterationStatisticsFromPage(){
        return new ArrayList<>(iterationStatistics.subList(page, page + 10));
    }

    public ArrayList<SimulationIterationStatistics> getIterationStatistics() {
        return iterationStatistics;
    }

    public void setIterationStatistics(ArrayList<SimulationIterationStatistics> iterationStatistics) {
        this.iterationStatistics = iterationStatistics;
    }
}
