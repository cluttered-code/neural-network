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
 * The {@code GeneticIndividual} interface describes the methods used during genetic algorithm training.
 *
 * @author David Clutter
 * @since 1.0.0
 */
public interface GeneticIndividual<I> extends GeneticElement {

    /**
     * Train this {@code GeneticIndividual} with the specified inputs.
     *
     * @param inputs The values used to train this {@code GeneticIndividual}.
     */
    public void train(final I inputs);

    /**
     * The fitness of this {@code GeneticIndividual} after training.
     *
     * @return The fitness value of this {@code GeneticIndividual}.
     */
    public double fitness();
}