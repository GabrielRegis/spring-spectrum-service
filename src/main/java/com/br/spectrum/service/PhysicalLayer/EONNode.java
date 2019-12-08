package com.br.spectrum.service.PhysicalLayer;


import com.br.spectrum.service.SharedLayer.Models.Coordinate;
import com.br.spectrum.service.SharedLayer.Models.Node;

public class EONNode {

    private String id;
    private Boolean shouldConvert;
    private String name;
    private Coordinate position;

    public EONNode(Node nodeConfig) {
        super();
        this.shouldConvert = nodeConfig.getShouldConvert();
        this.name = nodeConfig.getName();
        this.position = nodeConfig.getPosition();
        this.id = nodeConfig.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getShouldConvert() {
        return shouldConvert;
    }

    public void setShouldConvert(Boolean shouldConvert) {
        this.shouldConvert = shouldConvert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
