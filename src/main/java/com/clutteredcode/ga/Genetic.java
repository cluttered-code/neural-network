/**
 * (C) Copyright 2014 David Clutter (cluttered.code@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clutteredcode.ga;

/**
 * @author cluttered.code@gmail.com
 */
public interface Genetic<T extends Genetic<T>> {

    /**
     * Create a new child {@code Genetic} object using this and the specified {@code Genetic}
     * objects as parents.
     *
     * @param partner The {@code Genetic} object whose properties will be mixed with this
     *                {@code Genetic} object.
     * @return The newly created child {@code Genetic} object.
     */
    public T crossover(final T partner);

    /**
     * Create a new {@code Genetic} object by mutating this one with the specified rate.
     *
     * @return The newly created mutated {@code Genetic} object.
     */
    public T mutate(final double rate);
}