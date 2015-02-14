package com.clutteredcode.ga;

import java.util.Comparator;

/**
 * @author David Clutter
 */
public class GeneticIndividualComparator<T extends GeneticIndividual> implements Comparator<T> {

    @Override
    public int compare(final T individual1, final T individual2) {
        final double fitness1 = individual1.fitness();
        final double fitness2 = individual2.fitness();

        if (fitness1 < fitness2)
            return -1;

        if (fitness1 > fitness2)
            return 1;

        return 0;
    }
}