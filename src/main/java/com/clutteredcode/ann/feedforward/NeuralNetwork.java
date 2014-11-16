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
package com.clutteredcode.ann.feedforward;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cluttered.code@gmail.com
 */
public class NeuralNetwork {

    private List<Layer> layers;

    public NeuralNetwork(final List<Layer> layers) {
        this.layers = layers;
    }

    public Deque<List<Double>> fire(final List<Double> inputs) {
        final LinkedList<List<Double>> layerOutputs = new LinkedList<>();
        for (final Layer layer : layers) {
            final List<Double> input = layerOutputs.isEmpty() ? inputs : layerOutputs.getLast();
            final List<Double> output = layer.fire(input);
            layerOutputs.add(output);
        }

        return layerOutputs;
    }
}