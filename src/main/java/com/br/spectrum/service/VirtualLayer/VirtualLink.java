package com.br.spectrum.service.VirtualLayer;

import com.br.spectrum.service.PhysicalLayer.EONNode;
import com.br.spectrum.service.PhysicalLayer.EONSubcarrierSlot;
import com.br.spectrum.service.PhysicalLayer.Interfaces.EONSubcarrierSlotInterface;

import java.util.ArrayList;
import java.util.UUID;

public class VirtualLink {

    private String id;
    private EONNode sourceNode;
    private EONNode destinationNode;
    private ArrayList<EONSubcarrierSlotInterface> allocatedSubcarrierSlots;

    public VirtualLink(EONNode sourceNode, EONNode destinationNode, ArrayList<EONSubcarrierSlot> slots) {
        this.id = UUID.randomUUID().toString();
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        allocatedSubcarrierSlots = new ArrayList<EONSubcarrierSlotInterface>();
        for (EONSubcarrierSlot slot : slots){
            this.allocatedSubcarrierSlots.add(slot);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<EONSubcarrierSlotInterface> getSlots() {
        return allocatedSubcarrierSlots;
    }

    public void setSlots(ArrayList<EONSubcarrierSlotInterface> slots) {
        this.allocatedSubcarrierSlots = slots;
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
}
