package com.br.spectrum.service.domain;

public class SimulationSnapshotRequest {
    private int load;
    private int page;

    public SimulationSnapshotRequest() {
    }

    public SimulationSnapshotRequest(int load, int page) {
        this.load = load;
        this.page = page;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
