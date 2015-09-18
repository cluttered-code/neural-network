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
package com.clutteredcode.ann.neuron;

import com.clutteredcode.ann.activation.Activation;
import com.clutteredcode.ga.GeneticElement;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
 * @author David Clutter
 * @since 1.0.0
 */
public class Neuron implements GeneticElement<Neuron> {

    public static final int MINIMUM_BOUND = -100;
    public static final int MAXIMUM_BOUND = 100;

    private final Random random;

    private final Activation activation;
    private final double bias;
    private final List<Double> inputWeights;

    /**
     * Construct a new {@code Neuron} object with the specified {@code Activation}.
     * A bounded random number of input weights will be generated for the specified numInputs.
     * A bounded random number will also be generated for the bias.
     *
     * @param activation The {@code Activation} used to normalized this {@code Neuron} object's output.
     * @param numInputs  The number of random input weights to be generated.
     */
    public Neuron(final Activation activation, final int numInputs) {
        this.activation = activation;
        random = new Random();
        bias = randomBoundedDouble();
        inputWeights = IntStream.range(0, numInputs)
                .parallel()
                .mapToDouble(i -> randomBoundedDouble())
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Construct a new {@code Neuron} object with the specified parameters.
     *
     * @param activation   The {@code Activation} used to normalized this {@code Neuron} object's output.
     * @param bias         The bias used while calculating this {@code Neuron} object's output.
     * @param inputWeights the weights applied to each of this {@code Neuron} object's inputs.
     */
    public Neuron(final Activation activation, final double bias, final List<Double> inputWeights) {
        this.activation = activation;
        this.bias = bias;
        this.inputWeights = inputWeights;
        random = new Random();
    }

    private double randomBoundedDouble() {
        return random.nextDouble() * (MAXIMUM_BOUND - MINIMUM_BOUND) + MINIMUM_BOUND;
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
        for(final Double inputWeight : inputWeights) {
            dotProduct += inputWeight * inputsIterator.next();
        }
        return dotProduct;
    }

    @Override
    public Neuron mutate(final double mutationRate) {
        final Activation mutatedActivation = random.nextDouble() < mutationRate ? Activation.random() : activation;
        final double mutatedBias = random.nextDouble() < mutationRate ? randomBoundedDouble() : bias;
        final List<Double> mutatedWeights = inputWeights.stream()
                .map(weight -> random.nextDouble() < mutationRate ? randomBoundedDouble() : weight)
                .collect(Collectors.toList());
        return new Neuron(mutatedActivation, mutatedBias, mutatedWeights);
    }

    @Override
    public Neuron crossover(final Neuron mate) {
        final Activation crossoverActivation = random.nextBoolean() ? mate.activation : activation;
        final double crossoverBias = random.nextBoolean() ? mate.bias : bias;
        final List<Double> crossoverWeights = IntStream.range(0, inputWeights.size())
                .mapToObj(i -> random.nextBoolean() ? mate.inputWeights.get(i) : inputWeights.get(i))
                .collect(Collectors.toList());
        return new Neuron(crossoverActivation, crossoverBias, crossoverWeights);
    }
}