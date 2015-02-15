package com.clutteredcode.ann.activation;

/**
 * @author David Clutter
 */
public enum ActivationType {
    LINEAR,
    SIGMOID,
    TAN_H;

    private static final ActivationFunction LINEAR_FUNCTION = input -> input;
    private static final ActivationFunction SIGMOID_FUNCTION = input -> 1 / (1 + Math.exp(-input));
    private static final ActivationFunction TAN_H_FUNCTION = Math::tanh;

    public ActivationFunction getActivationFunction() {
        if (this == TAN_H)
            return TAN_H_FUNCTION;

        if (this == SIGMOID)
            return SIGMOID_FUNCTION;

        return LINEAR_FUNCTION;
    }

    public static ActivationType random() {
        return values()[(int) (Math.random() * values().length)];
    }
}