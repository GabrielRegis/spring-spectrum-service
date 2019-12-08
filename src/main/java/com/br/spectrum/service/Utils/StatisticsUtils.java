package com.br.spectrum.service.Utils;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class StatisticsUtils {
    public static double getConfidenceIntervalAbs(double[] values){
        Mean mean = StatisticsUtils.getMean(values);
        double meanResult = mean.getResult();
        double zValue = 1.96;
        double n = mean.getN();
        StandardDeviation standardDeviation = StatisticsUtils.getStandardDeviation(values);
        double standardDeviationResult = standardDeviation.getResult();
        return meanResult + zValue*(standardDeviationResult/Math.sqrt(n));
    }

    public static Mean getMean(double[] values){
        Mean mean = new Mean();
        for (double value : values) {
            mean.increment(value);
        }
        return mean;
    }

    public static Mean getIntegerMean(Integer[] values){
        Mean mean = new Mean();
        for (int value : values) {
            mean.increment(value);
        }
        return mean;
    }

    public static StandardDeviation getStandardDeviation(double[] values){
        StandardDeviation standardDeviation = new StandardDeviation();
        for (double value : values) {
            standardDeviation.increment(value);
        }
        return standardDeviation;

    } public static StandardDeviation getConfidenceIntervalAbsInteger(Integer[] values){
        StandardDeviation standardDeviation = new StandardDeviation();
        for (int value : values) {
            standardDeviation.increment(value);
        }
        return standardDeviation;
    }
}
