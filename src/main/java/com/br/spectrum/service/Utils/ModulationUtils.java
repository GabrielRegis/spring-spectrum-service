package com.br.spectrum.service.Utils;

import com.br.spectrum.service.PhysicalLayer.Modulation;

public class ModulationUtils {
    public static Modulation BPSK = new Modulation("BPSK", 8000, 1);
    public static Modulation QPSK = new Modulation("QPSK", 4000, 2);
    public static Modulation _8QAM = new Modulation("_8QAM", 2000, 3);
    public static Modulation _16QAM = new Modulation("_16QAM", 1000, 4);
    public static Modulation _32QAM = new Modulation("_32QAM", 500, 5);
    public static Modulation _64QAM = new Modulation("_64QAM", 250, 6);
    public static Modulation _128QAM = new Modulation("_128QAM", 125, 7);
    public static Modulation _256QAM = new Modulation("_256QAM", 62, 8);

    public static int bandwidthToSlots(double bandwidth, Modulation modulation, double slotSize){
        return (int) Math.ceil(bandwidth/(slotSize * modulation.getCapacityFactor()));
    };

    public static Modulation getModulationFromDistance(double distance){
        if (distance > BPSK.getReach()) {
            return null;
        } else {
            if (distance <= _256QAM.getReach()) {
                return _256QAM;
            } else {
                if (distance <= _128QAM.getReach()) {
                    return _128QAM;
                } else {
                    if (distance <= _64QAM.getReach()) {
                        return _64QAM;
                    } else {
                        if (distance <= _32QAM.getReach()) {
                            return _32QAM;
                        } else {
                            if (distance <= _16QAM.getReach()) {
                                return _16QAM;
                            } else {
                                if (distance <= _8QAM.getReach()) {
                                    return _8QAM;
                                } else {
                                    if (distance <= QPSK.getReach()) {
                                        return QPSK;
                                    } else {
                                        return BPSK;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
