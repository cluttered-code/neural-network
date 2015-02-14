package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationType;

import java.util.Collections;

/**
 * @author David Clutter
 */
public class InputNeuron extends Neuron {

    public InputNeuron() {
        super(ActivationType.LINEAR, 0.0, Collections.singletonList(1.0));
    }

    @Override
    public InputNeuron mutate(final double rate) {
        throw new UnsupportedOperationException("Can't mutate an input neuron");
    }

    @Override
    public Neuron crossover(final Neuron mate) {
        throw new UnsupportedOperationException("Can't crossover an input neuron");
    }
}