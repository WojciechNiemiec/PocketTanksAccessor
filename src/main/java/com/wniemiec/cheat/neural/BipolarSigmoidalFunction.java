package com.wniemiec.cheat.neural;

public class BipolarSigmoidalFunction implements ActivationFunction {

    /**
     * Learning speed
     */
//    private double beta;

    public BipolarSigmoidalFunction() {
//        beta = 0.1;
    }

//    public BipolarSigmoidalFunction(double beta) {
//        if (isValid(beta)) {
//            this.beta = beta;
//        }
//    }

    @Override
    public final Double apply(Double x) {
        return  (1 - Math.pow(Math.E, -x)) /
                (1 + Math.pow(Math.E, -x));
    }

//    private boolean isValid(double beta) {
//        return beta > 0 && beta <= 1;
//    }

    @Override
    public Double applyDerivative(Double x) {
        return 0.5 * (1 - Math.pow(x, 2.0));
    }
}
