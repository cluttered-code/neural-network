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
package com.clutteredcode.ann.network.feedforward;

import com.clutteredcode.ann.neuron.Neuron;
import com.clutteredcode.ga.GeneticElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Clutter
 */
public class Layer implements GeneticElement<Layer> {

    protected List<? extends Neuron> neurons;

    public Layer(final List<? extends Neuron> neurons) {
        this.neurons = neurons;
    }

    public List<Double> fire(final List<Double> inputs) {
        final List<Double> outputs = new ArrayList<>(neurons.size());
        for (final Neuron neuron : neurons) {
            final double output = neuron.fire(inputs);
            outputs.add(output);
        }
        return outputs;
    }

    @Override
    public Layer mutate(final double mutationRate) {
        final List<Neuron> mutatedNeuronList = new ArrayList<>(neurons.size());
        for (final Neuron neuron : neurons) {
            final Neuron mutatedNeuron = neuron.mutate(mutationRate);
            mutatedNeuronList.add(mutatedNeuron);
        }
        return new Layer(mutatedNeuronList);
    }

    @Override
    public Layer crossover(final Layer mate) {
        final List<Neuron> crossoverNeuronList = new ArrayList<>(neurons.size());
        final Iterator<? extends Neuron> mateNeuronIterator = mate.neurons.iterator();
        for (final Neuron neuron : neurons) {
            final Neuron mateNeuron = mateNeuronIterator.next();
            final Neuron crossoverNeuron = neuron.crossover(mateNeuron);
            crossoverNeuronList.add(crossoverNeuron);
        }
        return new Layer(crossoverNeuronList);
    }
}