package com.clutteredcode.ga;

/**
 * @author David Clutter
 */
public interface GeneticElement<T extends GeneticElement> {

    public T mutate(final double rate);

    public T crossover(final T mate);
}