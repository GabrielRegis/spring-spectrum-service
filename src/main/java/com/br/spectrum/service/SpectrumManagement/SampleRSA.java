package com.br.spectrum.service.SpectrumManagement;


import com.br.spectrum.service.EventManagement.Call;
import com.br.spectrum.service.EventManagement.Interfaces.ControlPlaneInterface;
import com.br.spectrum.service.PhysicalLayer.*;
import com.br.spectrum.service.SharedLayer.Models.CallDegradationConfiguration;
import com.br.spectrum.service.SpectrumManagement.Interfaces.RSA;
import com.br.spectrum.service.Utils.ModulationUtils;
import com.br.spectrum.service.VirtualLayer.EONVirtualTopology;
import com.br.spectrum.service.VirtualLayer.Lightpath;
import com.br.spectrum.service.VirtualLayer.VirtualLink;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SampleRSA implements RSA {

    public boolean tryToAllocateCall(ControlPlaneInterface controlPlaneInterface, EONPhysicalTopology physicalTopology,
                                     EONVirtualTopology eonVirtualTopology, Call call, boolean shouldDegradeBandwidth){
        KShortestPaths<EONNode, EONPhysicalLink> kShortestPaths = new KShortestPaths<EONNode, EONPhysicalLink>(physicalTopology, call.getSource(), 3);
        List<GraphPath<EONNode, EONPhysicalLink>> paths = kShortestPaths.getPaths(call.getDestination());

        if(paths.size() > 0){
            OUTER:
            for(GraphPath<EONNode, EONPhysicalLink> path : paths){
                List<EONPhysicalLink> links = path.getEdgeList();
                HashMap<String, VirtualLink> virtualLinkHashMap = new HashMap<String, VirtualLink>();

                for(EONPhysicalLink link : links){
                    Modulation bestModulation = ModulationUtils.getModulationFromDistance(link.getWeight());
                    int requiredSlots = ModulationUtils.bandwidthToSlots(call.getCallClass().getRequiredBandwidth(), bestModulation, link.getSlotSize());
                            ArrayList<EONSubcarrierSlot> nFreeSlots = link.getFirstNFreeSlots(requiredSlots);
                    if(nFreeSlots.size() > 0){
                        VirtualLink virtualLink = new VirtualLink(link.getSource(), link.getDestination(), nFreeSlots);
                        virtualLinkHashMap.put(virtualLink.getId(), virtualLink);
                    }else{
                        continue OUTER;
                    }
                }
                Lightpath lightpath = new Lightpath(call.getSource(), call.getDestination(), virtualLinkHashMap);
                call.getLightpaths().put(lightpath.getId(), lightpath);
                eonVirtualTopology.getLightpaths().put(lightpath.getId(), lightpath);
                controlPlaneInterface.allocateCall(call);
                return true;
            }
        }
        return false;
    };

    @Override
    public void callArrival(ControlPlaneInterface controlPlaneInterface, EONPhysicalTopology physicalTopology, EONVirtualTopology eonVirtualTopology, Call call) {
        //
        CallDegradationConfiguration callDegradationConfiguration = call.getCallClass().getDegradationConfiguration();
        boolean isDegradationTolerant = callDegradationConfiguration.isDegradationTolerant();
        boolean isBandwidthDegradationTolerant = isDegradationTolerant && callDegradationConfiguration.getBandwidthDegradationRate() > 0;
        boolean isDelayTolerant = isDegradationTolerant && callDegradationConfiguration.getDelayToleranceRate() > 0;

        // Try to allocate with full bandwidth
        if(tryToAllocateCall(controlPlaneInterface, physicalTopology, eonVirtualTopology, call, false)){
            return;
        }
        if(isBandwidthDegradationTolerant && tryToAllocateCall(controlPlaneInterface, physicalTopology, eonVirtualTopology, call, true)){
            return;
        }
        if(isDelayTolerant){
            controlPlaneInterface.delayCall(call);
            return;
        }
        controlPlaneInterface.blockCall(call);
    }

    @Override
    public void callDeparture(ControlPlaneInterface simulationObserverInterface, EONVirtualTopology eonVirtualTopology, EONPhysicalTopology physicalTopology, Call call) {
        //
    }
}
