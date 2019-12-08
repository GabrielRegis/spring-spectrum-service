package com.br.spectrum.service.VirtualLayer;
import com.br.spectrum.service.VirtualLayer.Interfaces.EONVirtualTopologyInterface;

import java.util.HashMap;

public class EONVirtualTopology implements EONVirtualTopologyInterface {

    private HashMap<String, Lightpath> lightpaths;

    public EONVirtualTopology(){
        lightpaths = new HashMap<String, Lightpath>();
    }

    @Override
    public void allocateLightpath(Lightpath lightpath) {
        lightpath.allocateLightpath();
        this.getLightpaths().put(lightpath.getId(), lightpath);
    }

    @Override
    public void freeLightpath(Lightpath lightpath) {
        lightpath.freeLightpath();
        this.getLightpaths().remove(lightpath.getId());
    }

    public HashMap<String, Lightpath> getLightpaths() {
        return lightpaths;
    }

    public void setLightpaths(HashMap<String, Lightpath> lightpaths) {
        this.lightpaths = lightpaths;
    }
}
