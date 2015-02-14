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
        final Layer layer = new Layer(mutatedNeurons);

        return layer;
    }

    @Override
    public Layer crossover(final Layer mate) {
        final List<Neuron> crossoverNeurons = IntStream.range(0, neurons.size())
                .mapToObj(i -> neurons.get(i).crossover(mate.neurons.get(i)))
                .collect(Collectors.toList());
        final Layer layer = new Layer(crossoverNeurons);

        return layer;
    }
}