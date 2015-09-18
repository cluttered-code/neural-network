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
package com.clutteredcode.ann.network.feedforward;

import com.clutteredcode.ann.neuron.InputNeuron;
import com.clutteredcode.ann.neuron.Neuron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Clutter
 */
public class InputLayer extends Layer {

    public InputLayer(final List<InputNeuron> neurons) {
        super(neurons);
    }

    @Override
    public List<Double> fire(final List<Double> inputs) {
        if (inputs.size() != neurons.size()) {
            throw new IllegalArgumentException("inputs (" + inputs.size() + ") and Neurons (" + neurons.size() + ") in the input layer must have the same number of elements");
        }
        final List<Double> outputs = new ArrayList<>(neurons.size());
        final Iterator<Double> inputsIterator = inputs.iterator();
        for (final Neuron neuron : neurons) {
            final double input = inputsIterator.next();
            final double output = neuron.fire(input);
            outputs.add(output);
        }
        return outputs;
    }

    /**
     * Returns {@code this InputLayer} without mutating.
     *
     * @param mutationRate This parameter is ignored.
     * @return {@code this InputLayer}.
     */
    @Override
    public Layer mutate(final double mutationRate) {
        return this;
    }

    /**
     * Returns {@code this InputLayer} without performing crossover.
     *
     * @param mate This parameter is ignored.
     * @return {@code this InputLayer}.
     */
    @Override
    public Layer crossover(final Layer mate) {
        return this;
    }
}