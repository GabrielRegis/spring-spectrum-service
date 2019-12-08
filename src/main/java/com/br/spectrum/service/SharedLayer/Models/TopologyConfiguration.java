package com.br.spectrum.service.SharedLayer.Models;

import java.util.ArrayList;
import java.util.UUID;

public class TopologyConfiguration {
    private String id;
    private String name;
    private ArrayList<Node> nodes;
    private ArrayList<Link> links;

    public TopologyConfiguration(){

    }

    public TopologyConfiguration(ArrayList<Node> nodes, ArrayList<Link> links) {
        this.id = UUID.randomUUID().toString();
        this.name = this.id;
        this.nodes = nodes;
        this.links = links;
    }

    public TopologyConfiguration(String name, ArrayList<Node> nodes, ArrayList<Link> links) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nodes = nodes;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }
}
