package com.clutteredcode.ann.activation;

/**
 * @author david.clutter@ca.com
 */
@FunctionalInterface
public interface ActivationFunction {
    double evaluate(final double input);
}