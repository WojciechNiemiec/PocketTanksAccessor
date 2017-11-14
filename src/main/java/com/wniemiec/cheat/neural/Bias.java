package com.wniemiec.cheat.neural;

public class Bias implements Synapse {

    private static final long serialVersionUID = 3968385770335006933L;

    private final static Double BIAS = 1.0;

    @Override
    public Double get() {
        return BIAS;
    }
}
