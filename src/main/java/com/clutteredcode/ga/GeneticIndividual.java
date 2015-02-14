package com.clutteredcode.ga;

/**
 * @author David Clutter
 */
public interface GeneticIndividual<T extends GeneticIndividual, I> extends GeneticElement<T> {

    public void train(final I inputs);

    public double fitness();
}