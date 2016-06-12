package com.stock.market.utils;

import java.util.List;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class MathUtils {

    /**
     * Calculate geometric mean
     *
     * @param numbers
     * @return
     */
    public static Double geometricMean(List<Double> numbers) {
        Double product = 1.0;

        for(Double number : numbers) {
            product = product * number;
        }

        Double geometricMean = Math.pow(product, 1.0/numbers.size());

        return Math.abs(geometricMean);
    }

    /**
     * Round to 4 decimal places
     *
     * @param val
     * @return
     */
    public static Double roundTo4Decimal(Double val) {
        if (val != null) {
            return Math.round(val * 10000.0) / 10000.0;
        }
        return val;
    }

}
