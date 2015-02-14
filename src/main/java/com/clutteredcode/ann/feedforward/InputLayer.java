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