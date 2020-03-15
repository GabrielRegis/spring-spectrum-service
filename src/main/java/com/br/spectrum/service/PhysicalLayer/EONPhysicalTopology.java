package com.br.spectrum.service.PhysicalLayer;

import com.br.spectrum.service.SharedLayer.Models.Link;
import com.br.spectrum.service.SharedLayer.Models.Node;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.ArrayList;
import java.util.Set;

public class EONPhysicalTopology extends WeightedMultigraph<EONNode, EONPhysicalLink>{

    private double lastCalculatedFragmentation;

    public EONPhysicalTopology(ArrayList<Node> nodes, ArrayList<Link> links){
        super(EONPhysicalLink.class);
        this.lastCalculatedFragmentation = 0;
        nodes.forEach(node -> {
            this.addVertex(new EONNode(node));
        });

        links.forEach(link -> {
            EONNode nodeB = (EONNode) this.vertexSet().stream().filter(eonNode -> eonNode.getId().equals(link.getNodeB().getId())).findAny().orElse(new EONNode(link.getNodeA()));
            EONNode nodeA = (EONNode) this.vertexSet().stream().filter(eonNode -> eonNode.getId().equals(link.getNodeA().getId())).findAny().orElse(new EONNode(link.getNodeB()));
            ArrayList<EONNode> eonNodes = new ArrayList<EONNode>();
            eonNodes.add(nodeA);
            eonNodes.add(nodeB);

            EONPhysicalLink eonPhysicalLink = new EONPhysicalLink(link.getId(), link.getSlots(), link.getSlotSize(), link.getDistance(), eonNodes);
            this.addEdge(nodeA, nodeB, eonPhysicalLink);
        });
        System.out.println("TopologyGenerated");
    }

    public double getTopologyFragmentation(){
        Object[] links =  this.edgeSet().toArray();
        double topologyAccumulatedFragmentation = 0;
        for (Object objectLink : links) {
            EONPhysicalLink link = (EONPhysicalLink) objectLink;
            topologyAccumulatedFragmentation+= link.getLinkFragmentationRate();
        }
        double fragmentation = topologyAccumulatedFragmentation/links.length;
        this.lastCalculatedFragmentation = fragmentation;
        return fragmentation;
    }

    public int getFreeSlotsSize(){
        int num = 0;
        for (EONPhysicalLink link : this.edgeSet()){
            num += link.getFreeSlotsNumber();
        }
        return num;
    }

    public double getLastCalculatedFragmentation() {
        return lastCalculatedFragmentation;
    }

    public void setLastCalculatedFragmentation(double lastCalculatedFragmentation) {
        this.lastCalculatedFragmentation = lastCalculatedFragmentation;
    }
}