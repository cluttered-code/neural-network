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
package com.clutteredcode.ann;

import java.util.List;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardLayer {

    /* ******** Instance Variables ******** */
    protected List<Neuron> neurons;

    /**
     * Complete Constructor creates a {@code FeedForwardLayer} instance with the specified parameters.
     *
     * @param neurons The {@link com.clutteredcode.ann.Neuron} objects in this {@code FeedForwardLayer}.
     */
    public FeedForwardLayer(final List<Neuron> neurons) {
        this.neurons = neurons;
    }

    /**
     * Returns the outputs of the {@code fire()} method of each {@link com.clutteredcode.ann.Neuron}.
     *
     * @param inputs The inputs being fed to this {@code FeedForwardLayer}.
     * @return The outputs.
     */
    public double[] fire(final double[] inputs) {
        return neurons.stream()
                .mapToDouble(neuron -> neuron.fire(inputs))
                .toArray();
    }
}