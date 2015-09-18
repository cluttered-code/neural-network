package com.clutteredcode.ga;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by david on 9/18/15.
 */
public class GeneticIndividualTest {

    private static final double BIG_FITNESS = 100.0;
    private static final double SMALL_FITNESS = 0.0;

    @Test
    public void testCompareToGreaterThan() {
        final TestableGeneticIndividual bigIndividual = new TestableGeneticIndividual(BIG_FITNESS);
        final TestableGeneticIndividual smallIndividual = new TestableGeneticIndividual(SMALL_FITNESS);

        final int result = bigIndividual.compareTo(smallIndividual);

        assertEquals(1, result);
    }

    @Test
    public void testCompareToLessThan() {
        final TestableGeneticIndividual bigIndividual = new TestableGeneticIndividual(BIG_FITNESS);
        final TestableGeneticIndividual smallIndividual = new TestableGeneticIndividual(SMALL_FITNESS);

        final int result = smallIndividual.compareTo(bigIndividual);

        assertEquals(-1, result);
    }

    @Test
    public void testCompareToEqual() {
        final TestableGeneticIndividual individual = new TestableGeneticIndividual(BIG_FITNESS);

        final int result = individual.compareTo(individual);

        assertEquals(0, result);
    }

    public static class TestableGeneticIndividual implements GeneticIndividual<Integer> {

        private final double fitness;

        public TestableGeneticIndividual(final double fitness) {
            this.fitness = fitness;
        }

        @Override
        public void train(Integer inputs) {

        }

        @Override
        public double fitness() {
            return fitness;
        }

        @Override
        public GeneticElement mutate(double mutationRate) {
            return null;
        }

        @Override
        public GeneticElement crossover(GeneticElement mate) {
            return null;
        }
    }
}