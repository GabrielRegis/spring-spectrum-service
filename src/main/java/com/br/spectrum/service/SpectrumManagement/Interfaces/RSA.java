package com.br.spectrum.service.SpectrumManagement.Interfaces;


import com.br.spectrum.service.EventManagement.Call;
import com.br.spectrum.service.EventManagement.Interfaces.ControlPlaneInterface;
import com.br.spectrum.service.PhysicalLayer.EONPhysicalTopology;
import com.br.spectrum.service.VirtualLayer.EONVirtualTopology;

public interface RSA {
    public void callArrival(ControlPlaneInterface controlPlaneInterface, EONPhysicalTopology physicalTopology, EONVirtualTopology eonVirtualTopology, Call call);
    public void callDeparture(ControlPlaneInterface simulationObserverInterface, EONVirtualTopology eonVirtualTopology, EONPhysicalTopology physicalTopology, Call call);

}
