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
    public NeuralNetwork mutate(double rate) {
        final List<Layer> mutatedLayers = IntStream.range(0, layers.size())
                .mapToObj(i -> {
                    if (i == 0) {
                        return layers.get(i);
                    } else return layers.get(i).mutate(rate);
                })
                .collect(Collectors.toList());
        final NeuralNetwork network = new NeuralNetwork(mutatedLayers);

        return network;
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
        final NeuralNetwork network = new NeuralNetwork(crossoverLayers);

        return network;
    }
}