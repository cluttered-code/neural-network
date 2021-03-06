package com.clutteredcode.ann.activation;

/**
 * @author cluttered.code@gmail.com
 */
@FunctionalInterface
public interface ActivationFunction {
    double evaluate(final double input);
}