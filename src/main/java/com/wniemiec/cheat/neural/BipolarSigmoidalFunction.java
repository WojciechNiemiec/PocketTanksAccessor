package com.wniemiec.cheat.neural;

public class BipolarSigmoidalFunction implements ActivationFunction {

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
}
