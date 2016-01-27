package com.clutteredcode.ann.neuron;

import com.clutteredcode.ann.activation.Activation;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code Neuron} class represents a neuron in the neural network, or brain.
 * <p>
 * A {@code Neuron} is the most basic building block of the neural network.
 * </p>
 * <p>
 * A {@code Neuron} contains the following:
 * <ul>
 * <li>
 * <b>Input Weights</b> -
 * <em>Modify the affect each input will have on the output.</em>
 * </li>
 * <li>
 * <b>Bias</b> -
 * <em>Influence the overall output.</em>
 * </li>
 * <li>
 * <b>Activation Function</b> -
 * <em>Normalize the output.</em>
 * </li>
 * </ul>
 * <p>
 * <p>
 * The class {@code Neuron} includes methods for firing the the neuron, as well as, mutate and crossover
 * methods to facilitate the genetic algorithm training process.
 * </p>
 *
 * @author cluttered.code@gmail.com
 */
public class Neuron {

    private final Activation activation;
    private final double bias;
    private final List<Double> inputWeights;

    private Neuron(final Activation activation, final double bias, final List<Double> inputWeights) {
        this.activation = activation;
        this.bias = bias;
        this.inputWeights = inputWeights;
    }

    /**
     * Fire this {@code Neuron} with the specified {@code input}. The output of this {@code Neuron} is returned.
     *
     * @param input The input used to calculate the {@code Neuron} object's output.
     * @return The output of this {@code Neuron}.
     */
    public double fire(final double input) {
        final List<Double> inputs = Collections.singletonList(input);
        return fire(inputs);
    }

    /**
     * Fire with {@code Neuron} with the specified {@code input}. The output of this {@code Neuron} is returned.
     *
     * @param inputs The {@code list} of {@code Double} objects used to calculate the {@code Neuron} object's output.
     * @return The output of this {@code Neuron}.
     */
    public double fire(final List<Double> inputs) {
        final double biasDotProduct = dotProductWithWeights(inputs) - bias;
        return activation.evaluate(biasDotProduct);
    }

    private double dotProductWithWeights(final List<Double> inputs) {
        if (inputs.size() != inputWeights.size()) {
            throw new IllegalArgumentException("inputs (" + inputs.size() + ") and inputWeights (" + inputWeights.size() + ") must have the same number of elements");
        }
        double dotProduct = 0;
        final Iterator<Double> inputsIterator = inputs.iterator();
        for (final Double inputWeight : inputWeights) {
            dotProduct += inputWeight * inputsIterator.next();
        }
        return dotProduct;
    }
}