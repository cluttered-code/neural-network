package com.clutteredcode.ga;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

/**
 * @author David Clutter
 */
@RunWith(JMockit.class)
public class GeneticIndividualComparatorTest {

    @Tested
    @SuppressWarnings("unused")
    private GeneticIndividualComparator<GeneticIndividual> comparator;

    @Mocked
    @SuppressWarnings("unused")
    private GeneticIndividual individual1;

    @Mocked
    @SuppressWarnings("unused")
    private GeneticIndividual individual2;

    @Test
    public void testLessThan() {

        new Expectations() {{
            individual1.fitness(); result = 2;
            individual2.fitness(); result = 5;
        }};

        final int result = comparator.compare(individual1, individual2);

        assertEquals(-1, result);
    }

    @Test
    public void testGreaterThan() {

        new Expectations() {{
            individual1.fitness(); result = 5;
            individual2.fitness(); result = 2;
        }};

        final int result = comparator.compare(individual1, individual2);

        assertEquals(1, result);
    }

    @Test
    public void testEqual() {

        new Expectations() {{
            individual1.fitness(); result = 5;
            individual2.fitness(); result = 5;
        }};

        final int result = comparator.compare(individual1, individual2);

        assertEquals(0, result);
    }
}