package com.br.spectrum.service.PhysicalLayer;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class EONPhysicalLink extends DefaultWeightedEdge {

    private String id;
    private int slotsNumber;
    private double slotSize;
    private ArrayList<EONNode> nodes;
    private EONNode source;
    private EONNode destination;
    private double lastCalculatedFragmentation;
    private double weight;
    private HashMap<String, EONSubcarrierSlot> subcarrierSlots;

    public EONPhysicalLink(){
        super();
    }

    public EONPhysicalLink(String id, int slotsNumber, double slotSize, double distance, ArrayList<EONNode> nodes){
        super();
        this.id = id;
        this.slotsNumber = slotsNumber;
        this.slotSize = slotSize;
        this.nodes = nodes;
        this.source = nodes.get(0);
        this.destination = nodes.get(1);
        this.weight = distance;
        this.lastCalculatedFragmentation = 0;

        HashMap<String, EONSubcarrierSlot> subcarrierSlots = new HashMap<String, EONSubcarrierSlot>();
        for(int i = 0; i < slotsNumber; i++){
            EONSubcarrierSlot slot = new EONSubcarrierSlot(slotSize, i);
            subcarrierSlots.put(slot.getId(), slot);
        }
        this.subcarrierSlots = subcarrierSlots;
    }

    public double getLinkFragmentationRate(){
        Object[] objectSlots =  this.getSubcarrierSlots().values().toArray();
        int maxFreeSlots = 0;
        for (int i = 0; i<slotsNumber; i++){
            int freeSlots = 0;
            for (int j =0; j< slotsNumber; j++){
                EONSubcarrierSlot slot =(EONSubcarrierSlot) objectSlots[j] ;
                if(slot.isOccupied()){
                    maxFreeSlots = freeSlots > maxFreeSlots ? freeSlots : maxFreeSlots;
                    break;
                }else{
                    freeSlots++;
                }
            }
        }
        int freeSlots = this.getFreeSlotsNumber() > 0 ? this.getFreeSlotsNumber() : 1;
        double fragmentation = 1 - maxFreeSlots/freeSlots;
        this.lastCalculatedFragmentation = fragmentation;
        return fragmentation;
    }

    public double getLastCalculatedFragmentation() {
        return lastCalculatedFragmentation;
    }

    public void setLastCalculatedFragmentation(double lastCalculatedFragmentation) {
        this.lastCalculatedFragmentation = lastCalculatedFragmentation;
    }

    @Override
    public String toString() {
        return getId();
    }

    public EONNode getDestination() {
        return destination;
    }

    public void setDestination(EONNode destination) {
        this.destination = destination;
    }

    public int getSlotsNumber() {
        return slotsNumber;
    }

    public void setSlotsNumber(int slotsNumber) {
        this.slotsNumber = slotsNumber;
    }

    public EONNode getSource() {
        return source;
    }

    public void setSource(EONNode source) {
        this.source = source;
    }

    public ArrayList<EONSubcarrierSlot> getFirstNFreeSlots(int n){
        ArrayList<EONSubcarrierSlot> linkSlots = new ArrayList<EONSubcarrierSlot>(this.getSubcarrierSlots().values());
        Comparator<EONSubcarrierSlot> comparator = new Comparator<EONSubcarrierSlot>() {
            @Override
            public int compare(EONSubcarrierSlot o1, EONSubcarrierSlot o2) {
                return o1.getSlotPosition() > o2.getSlotPosition() ? 1 : -1;
            }
        };
        linkSlots.sort(comparator);
        ArrayList<EONSubcarrierSlot> slots = new ArrayList<EONSubcarrierSlot>();

        OUTER:
        for(int i =0; i < this.getSubcarrierSlots().size(); i++){
            int j = i;
            slots = new ArrayList<EONSubcarrierSlot>();
            while (slots.size() < n && j < this.getSubcarrierSlots().size()){
                EONSubcarrierSlot slot = linkSlots.get(j);
                if(slot.isOccupied){
                    continue OUTER;
                }
                slots.add(slot);
                j++;
            }
            return slots;
        }
        return slots;
    }

    public int getFreeSlotsNumber(){
        ArrayList<EONSubcarrierSlot> freeSlots = new ArrayList<EONSubcarrierSlot>(this.getSubcarrierSlots().values());
        freeSlots.removeIf(eonSubcarrierSlot -> eonSubcarrierSlot.isOccupied);
        return freeSlots.size();
    }

    public ArrayList<EONSubcarrierSlot> getFreeSlots(){
        ArrayList<EONSubcarrierSlot> freeSlots = new ArrayList<EONSubcarrierSlot>(this.getSubcarrierSlots().values());
        freeSlots.removeIf(eonSubcarrierSlot -> eonSubcarrierSlot.isOccupied);
        return freeSlots;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ArrayList<EONNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<EONNode> nodes) {
        this.nodes = nodes;
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

    public HashMap<String, EONSubcarrierSlot> getSubcarrierSlots() {
        return subcarrierSlots;
    }

    public void setSubcarrierSlots(HashMap<String, EONSubcarrierSlot> subcarrierSlots) {
        this.subcarrierSlots = subcarrierSlots;
    }
}
