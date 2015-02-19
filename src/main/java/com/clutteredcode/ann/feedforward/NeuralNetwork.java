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
package com.clutteredcode.ann.feedforward;

import com.clutteredcode.ga.GeneticElement;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author David Clutter
 */
public class NeuralNetwork implements GeneticElement<NeuralNetwork> {

    private List<Layer> layers;

    public NeuralNetwork(final List<Layer> layers) {
        if(!(layers.get(0) instanceof InputLayer))
            throw new IllegalArgumentException("First layer in network must be an input layer");
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

    @Override
    public NeuralNetwork mutate(final double mutationRate) {
        final List<Layer> mutatedLayers = IntStream.range(0, layers.size())
                .mapToObj(i -> {
                    if (i == 0) {
                        return layers.get(i);
                    } else return layers.get(i).mutate(mutationRate);
                })
                .collect(Collectors.toList());

        return new NeuralNetwork(mutatedLayers);
    }

    @Override
    public NeuralNetwork crossover(final NeuralNetwork mate) {
        final List<Layer> crossoverLayers = IntStream.range(0, layers.size())
                .mapToObj(i -> {
                    if (i == 0) {
                        return layers.get(i);
                    } else return layers.get(i).crossover(mate.layers.get(i));
                })
                .collect(Collectors.toList());

        return new NeuralNetwork(crossoverLayers);
    }
}