package com.clutteredcode.ann.activation;

/**
 * @author cluttered.code@gmail.com
 */
public enum ActivationType {
    LINEAR,
    SIGMOID,
    TAN_H;

    // Singleton lambda ActivationFunction objects
    private static final ActivationFunction LINEAR_FUNC = input -> input;
    private static final ActivationFunction SIGMOID_FUNC = input -> 1 / (1 + Math.exp(-input));
    private static final ActivationFunction TAN_H_FUNC = Math::tanh;

    /**
     * Returns the {@link com.clutteredcode.ann.activation.ActivationFunction} associated with this {@code ActivationType}.
     *
     * @return The {@link com.clutteredcode.ann.activation.ActivationFunction}.
     */
    public ActivationFunction getFunction() {
        if (this == TAN_H)
            return TAN_H_FUNC;

        if (this == SIGMOID)
            return SIGMOID_FUNC;

        return LINEAR_FUNC;
    }
}