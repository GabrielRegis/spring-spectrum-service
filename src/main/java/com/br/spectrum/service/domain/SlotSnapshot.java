package com.br.spectrum.service.domain;

import com.br.spectrum.service.PhysicalLayer.EONSubcarrierSlot;

import java.util.ArrayList;
import java.util.List;

public class SlotSnapshot {

    private String id;
    private String slotId;
    private boolean isOccupied;
    private List<LinkSnapshot> linkSnapshot = new ArrayList<>();

    public SlotSnapshot (EONSubcarrierSlot slot){
        this.slotId = slot.getId();
        this.isOccupied = slot.isOccupied();
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public List<LinkSnapshot> getLinkSnapshot() {
        return linkSnapshot;
    }

    public void setLinkSnapshot(List<LinkSnapshot> linkSnapshot) {
        this.linkSnapshot = linkSnapshot;
    }
}
