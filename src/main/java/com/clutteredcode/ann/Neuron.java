package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationFunction;
import com.clutteredcode.ann.activation.ActivationType;

/**
 * @author cluttered.code@gmail.com
 */
public class Neuron {

    /* ******** Instance Variables ******** */
    protected double bias;
    protected double[] weights;
    protected ActivationType activationType;
    protected ActivationFunction activationFunction;

    /**
     * Complete Constructor creates and {@code Neuron} instance with the specified parameters.
     *
     * @param activationType Specifies the {@code ActivationFunction} used when firing this {@code Neuron}
     * @param bias           The bias used to offset the firing of this {@code Neuron}.
     * @param weights        The weights given to the inputs when firing this {@code Neuron}.
     */
    public Neuron(final ActivationType activationType, final double bias, final double[] weights) {
        this.bias = bias;
        this.weights = weights;
        this.activationType = activationType;
        this.activationFunction = activationType.getFunction();
    }

    /**
     * Returns the output of this {@code Neuron} when given the specified inputs.
     *
     * @param inputs The inputs being fed to this {@code Neuron}.
     * @return The output.
     */
    public double fire(final double[] inputs) {
        return activationFunction.activate(dotProduct(inputs));
    }

    /**
     * Returns the dot product of the weights and inputs summed with the bias.
     *
     * @param inputs The inputs being fed to this {@code Neuron}.
     * @return The dot product of the weights and inputs summed with the bias.
     */
    private double dotProduct(final double[] inputs) {
        // Guard Clause: input and weight size mismatch
        if (inputs.length != weights.length)
            throw new IllegalArgumentException("inputs (" + inputs.length + ") and weights (" + weights.length + ") must have the same number of elements");

        double sum = bias;

        for (int i = 0; i < inputs.length; ++i)
            sum += weights[i] * inputs[i];

        return sum;
    }
}