package com.wniemiec.cheat.neural;

public class Input {
    private Synapse synapse;
    private Double weight;
    private Double error;

    public Input(Synapse synapse, Double weight) {
        this.synapse = synapse;
        this.weight = weight;
    }

    public Double toDouble() {
        return synapse.get() * weight;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }
}
