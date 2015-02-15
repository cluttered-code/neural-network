/*
 * Copyright 2015 David Clutter
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
package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationFunction;
import com.clutteredcode.ann.activation.ActivationType;
import com.clutteredcode.ga.GeneticElement;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author David Clutter
 */
public class Neuron implements GeneticElement<Neuron> {

    public static final int MINIMUM_BOUND = -100;
    public static final int MAXIMUM_BOUND = 100;

    private final Random random;

    private final ActivationType activationType;
    private final double bias;
    private final List<Double> weights;

    public Neuron(final ActivationType activationType, final int numInputs) {
        this.activationType = activationType;
        random = new Random();

        bias = randomBoundedDouble();

        weights = IntStream.range(0, numInputs)
                .parallel()
                .mapToDouble(i -> randomBoundedDouble())
                .boxed()
                .collect(Collectors.toList());
    }

    public Neuron(final ActivationType activationType, final double bias, final List<Double> weights) {
        this.activationType = activationType;
        this.bias = bias;
        this.weights = weights;
        random = new Random();
    }

    private double randomBoundedDouble() {
        return random.nextDouble() * (MAXIMUM_BOUND - MINIMUM_BOUND) + MINIMUM_BOUND;
    }

    public double fire(final double input) {
        final List<Double> inputs = Collections.singletonList(input);
        final double result = fire(inputs);

        return result;
    }

    public double fire(final List<Double> inputs) {
        final double biasDotProduct = dotProductWithWeights(inputs) - bias;
        final ActivationFunction activationFunction = activationType.getActivationFunction();
        final double result = activationFunction.evaluate(biasDotProduct);

        return result;
    }

    private double dotProductWithWeights(final List<Double> inputs) {
        if (inputs.size() != weights.size())
            throw new IllegalArgumentException("inputs (" + inputs.size() + ") and weights (" + weights.size() + ") must have the same number of elements");

        final double dotProduct = IntStream.range(0, weights.size())
                .parallel()
                .mapToDouble(i -> weights.get(i) * inputs.get(i))
                .sum();

        return dotProduct;
    }

    @Override
    public Neuron mutate(final double rate) {
        final ActivationType mutatedActivationType = random.nextDouble() < rate ? ActivationType.random() : activationType;
        final double mutatedBias = random.nextDouble() < rate ? randomBoundedDouble() : bias;
        final List<Double> mutatedWeights = weights.stream()
                .map(weight -> random.nextDouble() < rate ? randomBoundedDouble() : weight)
                .collect(Collectors.toList());
        final Neuron neuron = new Neuron(mutatedActivationType, mutatedBias, mutatedWeights);

        return neuron;
    }

    @Override
    public Neuron crossover(final Neuron mate) {
        final ActivationType crossoverActivationType = random.nextBoolean() ? mate.activationType : activationType;
        final double crossoverBias = random.nextBoolean() ? mate.bias : bias;
        final List<Double> crossoverWeights = IntStream.range(0, weights.size())
                .mapToObj(i -> random.nextBoolean() ? mate.weights.get(i) : weights.get(i))
                .collect(Collectors.toList());
        final Neuron neuron = new Neuron(crossoverActivationType, crossoverBias, crossoverWeights);

        return neuron;
    }
}