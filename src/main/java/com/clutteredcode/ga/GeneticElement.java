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

/**
 * The {@code GeneticElement} interface describes the methods used to modify genetic elements during
 * genetic algorithm training.
 *
 * @author David Clutter
 * @since 1.0.0
 */
public interface GeneticElement<T extends GeneticElement> {

    /**
     * Create a new mutated {@code GeneticElement} using the specified {@code rate} to create a new
     * {@code GeneticElement} and return the result.
     *
     * @param mutationRate The percentage used to determine how often a mutation should occur for each nested property.
     * @return the new mutated {@code GeneticElement}.
     */
    T mutate(final double mutationRate);

    /**
     * Create a new child {@code GeneticElement} by mixing together the properties of this {@code GeneticElement} and
     * the specified {@code GeneticElement} and returning the result.
     *
     * @param mate The {@code GeneticElement} used to crossover with this {@code GeneticElement}.
     * @return the new child {@code GeneticElement}.
     */
    T crossover(final T mate);
}