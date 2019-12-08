package com.br.spectrum.service.EventManagement;

import com.br.spectrum.service.PhysicalLayer.EONPhysicalTopology;
import com.br.spectrum.service.domain.LinkSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TopologySnapshot {

    private String id;
    private List<LinkSnapshot> linkSnapshots = new ArrayList<>();
    private double fragmentation;

    public TopologySnapshot(EONPhysicalTopology topology){
        this.id = UUID.randomUUID().toString();
        topology.edgeSet().forEach(link -> {
            LinkSnapshot linkSnapshot = new LinkSnapshot(link);
            this.getLinkSnapshots().add(linkSnapshot);
        });
        this.fragmentation = topology.getLastCalculatedFragmentation();
    }

    public double getFragmentation() {
        return fragmentation;
    }

    public void setFragmentation(double fragmentation) {
        this.fragmentation = fragmentation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LinkSnapshot> getLinkSnapshots() {
        return linkSnapshots;
    }

    public void setLinkSnapshots(List<LinkSnapshot> linkSnapshots) {
        this.linkSnapshots = linkSnapshots;
    }
}
