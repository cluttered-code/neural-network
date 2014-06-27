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
public class FeedForwardNetwork {

    /* ******** Instance Variables ******** */
    protected List<FeedForwardLayer> layers;

    /**
     * Complete Constructor creates a {@code FeedForwardNetwork} instance with the specified parameters.
     *
     * @param layers The {@link com.clutteredcode.ann.FeedForwardLayer} objects in this {@code FeedForwardNetwork}.
     */
    public FeedForwardNetwork(final List<FeedForwardLayer> layers) {
        this.layers = layers;
    }

    /**
     * Fire each {@link com.clutteredcode.ann.FeedForwardLayer}, piping the output of the previous into the next.
     *
     * @param inputs The inputs to be put into the first {@link com.clutteredcode.ann.FeedForwardLayer}.
     * @return The outputs of the last {@link com.clutteredcode.ann.FeedForwardLayer}.
     */
    public double[] fire(final double[] inputs) {
        double[] currentInputs = inputs;

        for (final FeedForwardLayer layer : layers)
            currentInputs = layer.fire(currentInputs);

        return currentInputs;
    }
}