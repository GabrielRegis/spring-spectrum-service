package com.br.spectrum.service.PhysicalLayer;


import com.br.spectrum.service.PhysicalLayer.Interfaces.EONSubcarrierSlotInterface;

import java.util.UUID;

public class EONSubcarrierSlot implements EONSubcarrierSlotInterface {

    private String id;
    private int slotPosition;
    boolean isOccupied;
    private double slotSize;

    public EONSubcarrierSlot(double slotSize, int slotPosition){
        this.id = UUID.randomUUID().toString();
        this.isOccupied = false;
        this.slotPosition = slotPosition;
        this.slotSize = slotSize;
    }

    public double getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(double slotSize) {
        this.slotSize = slotSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSlotPosition() {
        return slotPosition;
    }

    public void setSlotPosition(int slotPosition) {
        this.slotPosition = slotPosition;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public void freeSlot() {
        this.setOccupied(false);
    }

    @Override
    public void allocateSlot() {
        this.setOccupied(true);
    }
}
