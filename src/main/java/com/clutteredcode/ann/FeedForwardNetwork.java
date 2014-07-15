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

import java.util.ArrayList;
import java.util.LinkedList;
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

    public void backPropagationTraining(final double[][] inputs, final double[][] expectedOutputs) {
        // Guard Clause: input and output size mismatch
        if (inputs.length != expectedOutputs.length)
            throw new IllegalArgumentException("inputs (" + inputs.length + ") and expectedOutputs (" + expectedOutputs.length + ") must have the same number of elements");

        // Loop each set of inputs
        for (int i = 0; i < inputs.length; ++i) {

            // Run one set of inputs and collect outputs for each layer
            List<double[]> layerOutputs = new ArrayList<>();
            fireAndCollectOutputs(inputs[i], layerOutputs);

            // Reverse loop each neuron layer except input layer
            List<double[]> layerDeltas = new LinkedList<>();
            for (int j = layerOutputs.size() - 1; j > 0; --j) {

                // Loop each neuron output in layer
                double[] deltas = new double[layerOutputs.get(j).length];
                for (int k = 0; k < deltas.length; ++k) {

                    // Output layer propagation
                    if (layerDeltas.isEmpty()) {
                        deltas[k] = expectedOutputs[i][k] - layerOutputs.get(j)[k];
                    }

                    // Hidden layer propagation
                    else {
                        // TODO: hidden layer back propagation
                    }
                }
                layerDeltas.add(deltas);

            }
        }
    }

    /**
     * Fire each {@link com.clutteredcode.ann.FeedForwardLayer}, piping the output of the previous into the next.
     *
     * @param inputs The inputs to be put into the first {@link com.clutteredcode.ann.FeedForwardLayer}.
     * @return The outputs of the last {@link com.clutteredcode.ann.FeedForwardLayer}.
     */
    public double[] fire(final double[] inputs) {
        return fireAndCollectOutputs(inputs, new ArrayList<>());
    }

    protected double[] fireAndCollectOutputs(final double[] inputs, List<double[]> layerOutputs) {
        layerOutputs.add(inputs);

        for (final FeedForwardLayer layer : layers) {
            final double[] input = layerOutputs.get(layerOutputs.size() - 1);
            final double[] output = layer.fire(input);
            layerOutputs.add(output);
        }

        return layerOutputs.get(layerOutputs.size() - 1);
    }
}