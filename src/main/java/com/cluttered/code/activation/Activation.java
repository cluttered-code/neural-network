package com.cluttered.code.activation;

/**
 * @author david.clutter@ca.com
 */
public enum Activation implements ActivationFunction {
    LINEAR(input -> input),
    SIGMOID(input -> 1 / (1 + Math.exp(-input))),
    TAN_H(Math::tanh);

    private final ActivationFunction activationFunction;

    Activation(final ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public double evaluate(final double input) {
        return activationFunction.evaluate(input);
    }

    public static Activation random() {
        final Activation[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}