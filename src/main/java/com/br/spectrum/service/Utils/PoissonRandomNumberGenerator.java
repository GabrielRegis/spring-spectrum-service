package com.br.spectrum.service.Utils;


import com.br.spectrum.service.EventManagement.SimulationInstance;

import java.util.Random;

public class PoissonRandomNumberGenerator {

    public static double getPoissonRandomNumber(double lambda, long seed) {
        Random generator = SimulationInstance.randomGenerator;
        double randomPoissonNumber = (-Math.log(1.0f - generator.nextDouble()) / lambda)*4;
        System.out.println(randomPoissonNumber);
        return randomPoissonNumber;
    }
}
