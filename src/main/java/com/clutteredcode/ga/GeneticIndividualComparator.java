/*
 * Copyright © 2015 David Clutter
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

import java.util.Comparator;

/**
 * Compare two {@code GeneticIndividual} objects using their fitness values.
 *
 * @author David Clutter
 * @since 1.0.0
 */
public class GeneticIndividualComparator<T extends GeneticIndividual> implements Comparator<T> {

    /**
     * Compare the specified {@code GeneticIndividual} objects using their fitness values.
     *
     * @param individual1 The first {@code GeneticIndividual}.
     * @param individual2 The second {@code GeneticIndividual}.
     * @return A negative integer, zero, or a positive integer as the first argument is
     * less than, equal to, or greater than the second.
     */
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