package com.br.spectrum.service.domain;

import com.br.spectrum.service.PhysicalLayer.EONPhysicalLink;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LinkSnapshot {

    private String id;
    private String linkId;
    private boolean isDisrupted;
    private List<SlotSnapshot> slotSnapshots = new ArrayList<>();
    private double fragmentation;

    public LinkSnapshot(EONPhysicalLink link){
        this.id = UUID.randomUUID().toString();
        this.setLinkId(link.getId());
        link.getSubcarrierSlots().values().forEach(slot -> {
            SlotSnapshot slotSnapshot = new SlotSnapshot(slot);
            this.getSlotSnapshots().add(slotSnapshot);
        });
        this.fragmentation = link.getLastCalculatedFragmentation();
    }

    public double getFragmentation() {
        return fragmentation;
    }

    public void setFragmentation(double fragmentation) {
        this.fragmentation = fragmentation;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDisrupted() {
        return isDisrupted;
    }

    public void setDisrupted(boolean disrupted) {
        isDisrupted = disrupted;
    }

    public List<SlotSnapshot> getSlotSnapshots() {
        return slotSnapshots;
    }

    public void setSlotSnapshots(List<SlotSnapshot> slotSnapshots) {
        this.slotSnapshots = slotSnapshots;
    }
}
