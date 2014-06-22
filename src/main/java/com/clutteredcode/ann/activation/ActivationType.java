package com.clutteredcode.ann.activation;

/**
 * @author cluttered.code@gmail.com
 */
public enum ActivationType {
    LINEAR,
    SIGMOID,
    TAN_H;

    /**
     * Returns the {@link com.clutteredcode.ann.activation.ActivationFunction} associated with this {@code ActivationType}.
     *
     * @return the {@link com.clutteredcode.ann.activation.ActivationFunction}.
     */
    public ActivationFunction getFunction() {
        if (this.equals(TAN_H))
            return (final double input) -> Math.tanh(input);

        if (this.equals(SIGMOID))
            return (final double input) -> 1 / (1 + Math.exp(-input));

        // Default: LINEAR
        return (final double input) -> input;
    }
}