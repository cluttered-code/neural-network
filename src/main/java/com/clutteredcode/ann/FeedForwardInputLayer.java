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
public class FeedForwardInputLayer extends FeedForwardLayer {
    /**
     * Complete Constructor creates a {@code FeedForwardLayer} instance with the specified parameters.
     *
     * @param neurons The {@link com.clutteredcode.ann.Neuron} objects in this {@code FeedForwardLayer}.
     */
    public FeedForwardInputLayer(final List<Neuron> neurons) {
        super(neurons);
    }

    /**
     * Returns the outputs of the {@code fire()} method of each {@link com.clutteredcode.ann.Neuron}.
     *
     * @param inputs The inputs being fed to this {@code FeedForwardLayer}.
     * @return The outputs.
     */
    @Override
    public double[] fire(final double[] inputs) {
        final double[] outputs = new double[neurons.size()];

        for (int i = 0; i < inputs.length; ++i)
            outputs[i] = neurons.get(i).fire(inputs[i]);

        return outputs;
    }
}