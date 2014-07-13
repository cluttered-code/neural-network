/**
 * (C) Copyright 2014 David Clutter (cluttered.code@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationType;
import com.clutteredcode.ga.Genetic;

import java.util.Random;

/**
 * @author cluttered.code@gmail.com
 */
public class Neuron implements Genetic<Neuron> {

    /* ******** Protected Static Final Variables ******** */
    protected static final Random RANDOM = new Random();

    /* ******** Instance Variables ******** */
    protected ActivationType activationType;
    protected double bias;
    protected double[] weights;

    /**
     * Complete Constructor creates a {@code Neuron} instance with the specified parameters.
     *
     * @param activationType Specifies the {@code ActivationFunction} used when firing this {@code Neuron}.
     * @param bias           The bias used to offset the firing of this {@code Neuron}.
     * @param weights        The weights given to the inputs when firing this {@code Neuron}.
     */
    public Neuron(final ActivationType activationType, final double bias, final double[] weights) {
        this.activationType = activationType;
        this.bias = bias;
        this.weights = weights;
    }

    /**
     * Returns the output of this {@code Neuron} with the specified input.
     *
     * @param input The input being fed to this {@code Neuron}.
     * @return The output.
     */
    public double fire(final double input) {
        return fire(new double[]{input});
    }

    /**
     * Returns the output of this {@code Neuron} with the specified inputs.
     *
     * @param inputs The inputs being fed to this {@code Neuron}.
     * @return The output.
     */
    public double fire(final double[] inputs) {
        return activationType.getFunction().evaluate(bias + dotProduct(inputs));
    }

    /**
     * Returns the dot product of the inputs and weights.
     *
     * @param inputs The inputs being fed to this {@code Neuron}.
     * @return The dot product of the inputs and weights.
     */
    private double dotProduct(final double[] inputs) {
        // Guard Clause: input and weight size mismatch
        if (inputs.length != weights.length)
            throw new IllegalArgumentException("inputs (" + inputs.length + ") and weights (" + weights.length + ") must have the same number of elements");

        double sum = 0.0;
        for (int i = 0; i < inputs.length; ++i)
            sum += weights[i] * inputs[i];

        return sum;
    }

    @Override
    public Neuron crossover(final Neuron partner) {
        final ActivationType newActivationType = RANDOM.nextBoolean() ? activationType : partner.activationType;
        final double newBias = RANDOM.nextBoolean() ? bias : partner.bias;

        final double[] newWeights = new double[weights.length];
        for (int i = 0; i < weights.length; ++i)
            newWeights[i] = RANDOM.nextBoolean() ? weights[i] : partner.weights[i];

        return new Neuron(newActivationType, newBias, newWeights);
    }

    @Override
    public Neuron mutate(final double rate) {
        final ActivationType newActivationType = RANDOM.nextDouble() < rate ? ActivationType.random() : activationType;
        final double newBias = RANDOM.nextDouble() < rate ? RANDOM.nextDouble() : bias;

        final double[] newWeights = new double[weights.length];
        for (int i = 0; i < weights.length; ++i)
            newWeights[i] = RANDOM.nextDouble() < rate ? RANDOM.nextDouble() : weights[i];

        return new Neuron(newActivationType, newBias, newWeights);
    }
}