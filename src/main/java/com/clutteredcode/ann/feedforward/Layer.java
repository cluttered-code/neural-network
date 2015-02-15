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

import com.clutteredcode.ann.Neuron;
import com.clutteredcode.ga.GeneticElement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author David Clutter
 */
public class Layer implements GeneticElement<Layer> {

    protected List<? extends Neuron> neurons;

    public Layer(final List<? extends Neuron> neurons) {
        this.neurons = neurons;
    }

    public List<Double> fire(final List<Double> inputs) {
        final List<Double> outputs = neurons.stream()
                .map(neuron -> neuron.fire(inputs))
                .collect(Collectors.toList());

        return outputs;
    }

    @Override
    public Layer mutate(final double rate) {
        final List<Neuron> mutatedNeurons = neurons.stream()
                .map(neuron -> neuron.mutate(rate))
                .collect(Collectors.toList());

        return new Layer(mutatedNeurons);
    }

    @Override
    public Layer crossover(final Layer mate) {
        final List<Neuron> crossoverNeurons = IntStream.range(0, neurons.size())
                .mapToObj(i -> neurons.get(i).crossover(mate.neurons.get(i)))
                .collect(Collectors.toList());

        return new Layer(crossoverNeurons);
    }
}