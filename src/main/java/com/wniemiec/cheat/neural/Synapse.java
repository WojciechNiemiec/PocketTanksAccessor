package com.wniemiec.cheat.neural;

public class Synapse {
    private Inputable inputable;
    private Double weight;
    private Double error;

    Synapse(Inputable input, Double weight) {
        this.inputable = input;
        this.weight = weight;
    }

    Double toDouble() {
        return inputable.get() * weight;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }
}
