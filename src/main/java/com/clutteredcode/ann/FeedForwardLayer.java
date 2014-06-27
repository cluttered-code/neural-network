package com.clutteredcode.ann;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardLayer {

    /* ******** Instance Variables ******** */
    protected List<Neuron> neurons;

    /**
     * Complete Constructor creates a {@code FeedForwardLayer} instance with the specified parameters.
     *
     * @param neurons The {@link com.clutteredcode.ann.Neuron} objects in this {@code FeedForwardLayer}.
     */
    public FeedForwardLayer(final List<Neuron> neurons) {
        this.neurons = neurons;
    }

    /**
     * Returns the outputs of the {@code fire()} method of each {@link com.clutteredcode.ann.Neuron}.
     *
     * @param inputs The inputs being fed to this {@code FeedForwardLayer}.
     * @return The outputs.
     */
    public double[] fire(final double[] inputs) {
        return neurons.stream()
                .mapToDouble(neuron -> neuron.fire(inputs))
                .toArray();
    }
}