package com.br.spectrum.service.VirtualLayer.Interfaces;

import com.br.spectrum.service.VirtualLayer.Lightpath;

public interface EONVirtualTopologyInterface {
    public void allocateLightpath(Lightpath lightpath);
    public void freeLightpath(Lightpath lightpath);
}
