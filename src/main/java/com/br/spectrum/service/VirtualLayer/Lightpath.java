package com.br.spectrum.service.VirtualLayer;

import com.br.spectrum.service.PhysicalLayer.EONNode;
import com.br.spectrum.service.VirtualLayer.Interfaces.LightpathInterface;

import java.util.HashMap;
import java.util.UUID;

public class Lightpath implements LightpathInterface {

    private String id;
    private EONNode sourceNode;
    private EONNode destinationNode;
    private HashMap<String, VirtualLink> links;

    public Lightpath(EONNode sourceNode, EONNode destinationNode, HashMap<String, VirtualLink> virtualLinks) {
        this.id = UUID.randomUUID().toString();
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.links = virtualLinks;
    }


    @Override
    public void allocateLightpath() {
        this.links.forEach((id, virtualLink) -> {
            virtualLink.getSlots().forEach(eonSubcarrierSlotInterface -> {
                eonSubcarrierSlotInterface.allocateSlot();
            });
        });
    }

    @Override
    public void freeLightpath() {
        this.links.forEach((id, virtualLink) -> {
            virtualLink.getSlots().forEach(eonSubcarrierSlotInterface -> {
                eonSubcarrierSlotInterface.freeSlot();
            });
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EONNode getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(EONNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    public EONNode getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(EONNode destinationNode) {
        this.destinationNode = destinationNode;
    }

    public HashMap<String, VirtualLink> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, VirtualLink> links) {
        this.links = links;
    }
}
