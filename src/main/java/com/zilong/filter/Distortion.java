package com.zilong.filter;

import com.zilong.model.OutputRecord;

import java.util.Collection;

/**
 * This class use for generate data Distortion.
 *
 * requirement: Generalising the data introduces distortion. Define a measure(s) of data distortion
 *
 */
public class Distortion {

    /**
     * Thie method will calculate the distortion of the output records..
     *
     * 1. calculate the mean of the InputRecord's rawValue
     * 2. calculate Mean Variances
     * 3. calculate Anonymised Mean Variances
     *
     */
    public static double calculate(Collection<OutputRecord> outputRecords){

        //Average of rawValue
        final double mean = outputRecords.stream().mapToDouble(e -> e.getRawValue()).average().getAsDouble();

        //Sum of Square total， mean Variances
        double sst = outputRecords.stream().mapToDouble(e -> e.getRawValue() - mean).map(e -> e * e).sum();

        //Sum of Squares Error，Anonymised Mean Variances
        double sse = outputRecords.stream().mapToDouble(e -> e.getRawValue() - e.getAnonymisedValue()).map(e -> e * e).sum();

        return sse/sst;
    }
}
