package com.br.spectrum.service.EventManagement;

import com.br.spectrum.service.PhysicalLayer.EONNode;
import com.br.spectrum.service.VirtualLayer.Interfaces.LightpathInterface;

import java.util.HashMap;
import java.util.UUID;


public class Call {

    private String id;
    private CallClass callClass;
    private HashMap<String, LightpathInterface> lightpaths;
    private EONNode source;
    private EONNode destination;

    public Call(CallClass callClass) {
        this.callClass = callClass;
        this.id = UUID.randomUUID().toString();
        this.lightpaths = new HashMap<String, LightpathInterface>();
    }

    public Call(CallClass callClass, EONNode source, EONNode destination) {
        this.callClass = callClass;
        this.id = UUID.randomUUID().toString();
        this.source = source;
        this.destination = destination;
        this.lightpaths = new HashMap<String, LightpathInterface>();
    }

    public EONNode getSource() {
        return source;
    }

    public void setSource(EONNode source) {
        this.source = source;
    }

    public EONNode getDestination() {
        return destination;
    }

    public void setDestination(EONNode destination) {
        this.destination = destination;
    }

    public CallClass getCallClass() {
        return callClass;
    }

    public void setCallClass(CallClass callClass) {
        this.callClass = callClass;
    }

    public HashMap<String, LightpathInterface> getLightpaths() {
        return lightpaths;
    }

    public void setLightpaths(HashMap<String, LightpathInterface> lightpaths) {
        this.lightpaths = lightpaths;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
