package com.clutteredcode.ann.activation;

/**
 * @author cluttered.code@gmail.com
 */
public interface ActivationFunction {

    /**
     * Neuron activation function.
     *
     * @param input The input value.
     * @return The value computed by the activation function.
     */
    public double activate(final double input);
}