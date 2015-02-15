/*
 * Copyright 2015 David Clutter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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