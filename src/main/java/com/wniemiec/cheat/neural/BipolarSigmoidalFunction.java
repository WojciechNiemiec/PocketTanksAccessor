package com.wniemiec.cheat.neural;

public class BipolarSigmoidalFunction implements ActivationFunction {

    /**
     * Learning speed
     */
    private double beta;

    public BipolarSigmoidalFunction() {
        beta = 0.5;
    }

    public BipolarSigmoidalFunction(double beta) {
        if (isValid(beta)) {
            this.beta = beta;
        }
    }

    @Override
    public final Double apply(Double x) {
        return  (1 - Math.pow(Math.E, -beta * x)) /
                (1 + Math.pow(Math.E, -beta * x));
    }

    private boolean isValid(double beta) {
        return beta > 0 && beta <= 1;
    }

    @Override
    public Double applyDerivative(Double aDouble) {
        return beta * (1 - Math.pow(aDouble, 2.0));
    }
}
