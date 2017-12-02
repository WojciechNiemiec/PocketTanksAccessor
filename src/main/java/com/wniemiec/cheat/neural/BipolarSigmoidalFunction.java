package com.wniemiec.cheat.neural;

public class BipolarSigmoidalFunction implements ActivationFunction {

    @Override
    public final Double apply(Double x) {
        return  (1 - Math.pow(Math.E, -x)) /
                (1 + Math.pow(Math.E, -x));
    }

    @Override
    public Double applyDerivative(Double x) {
        return 0.5 * (1 - Math.pow(x, 2.0));
    }
}
