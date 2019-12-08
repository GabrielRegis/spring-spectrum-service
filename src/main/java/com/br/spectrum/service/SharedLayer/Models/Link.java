package com.br.spectrum.service.SharedLayer.Models;

import java.util.UUID;

public class Link {
    private String id;
    private String name;
    private int slots;
    private double slotSize;
    private double distance;
    private Node nodeA;
    private Node nodeB;
    private boolean bidirectional;

    public Link(){

    }

    public Link(int slots, double slotSize, double distance, Node nodeA, Node nodeB) {
        this.id = UUID.randomUUID().toString();
        this.name = this.id;
        this.slots = slots;
        this.slotSize = slotSize;
        this.distance = distance;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.bidirectional = true;
    }

    public Link(String name, int slots, double slotSize, double distance, Node nodeA, Node nodeB, boolean bidirectional) {
        this.name = name;
        this.slots = slots;
        this.slotSize = slotSize;
        this.distance = distance;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.bidirectional = bidirectional;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public double getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(double slotSize) {
        this.slotSize = slotSize;
    }

    public Node getNodeA() {
        return nodeA;
    }

    public void setNodeA(Node nodeA) {
        this.nodeA = nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public void setNodeB(Node nodeB) {
        this.nodeB = nodeB;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }
}
