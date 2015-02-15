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
package com.clutteredcode.ann.feedforward;

import com.clutteredcode.ann.InputNeuron;
import com.clutteredcode.ann.Neuron;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author David Clutter
 */
public class InputLayer extends Layer {

    public InputLayer(final List<InputNeuron> neurons) {
        super(neurons);
    }

    @Override
    public List<Double> fire(final List<Double> inputs) {
        if(inputs.size() != neurons.size())
            throw new IllegalArgumentException("inputs (" + inputs.size() + ") and Neurons (" + neurons.size() + ") in the input layer must have the same number of elements");

        return IntStream.range(0, inputs.size())
                .mapToDouble(i -> {
                    final double input = inputs.get(i);
                    final Neuron neuron = neurons.get(i);
                    return neuron.fire(input);
                })
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public Layer mutate(final double rate) {
        throw new UnsupportedOperationException("Can't mutate an input layer");
    }

    @Override
    public Layer crossover(final Layer mate) {
        throw new UnsupportedOperationException("Can't crossover an input layer");
    }
}