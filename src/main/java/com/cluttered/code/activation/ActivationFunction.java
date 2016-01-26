package com.cluttered.code.activation;

/**
 * @author david.clutter@ca.com
 */
@FunctionalInterface
public interface ActivationFunction {
    double evaluate(final double input);
}