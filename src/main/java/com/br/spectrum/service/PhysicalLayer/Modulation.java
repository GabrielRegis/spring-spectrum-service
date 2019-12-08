package com.br.spectrum.service.PhysicalLayer;

// Modulation class
public class Modulation {
    String name;

    // Reach in Kms
    int reach;

    double capacityFactor;

    public Modulation(String name, int reach, double capacityFactor) {
        this.name = name;
        this.reach = reach;
        this.capacityFactor = capacityFactor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public double getCapacityFactor() {
        return capacityFactor;
    }

    public void setCapacityFactor(double capacityFactor) {
        this.capacityFactor = capacityFactor;
    }
}
