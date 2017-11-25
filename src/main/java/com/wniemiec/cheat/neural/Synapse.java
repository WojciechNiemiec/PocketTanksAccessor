package com.wniemiec.cheat.neural;

import java.util.Objects;

public class Synapse {
    private Inputable inputable;
    private Double weight;
    private Double error;
    private Double lastOutput;

    Synapse(Inputable input, Double weight) {
        this.inputable = input;
        this.weight = weight;
    }

    Double toDouble() {
        return getLastOutput() * weight;
    }

    private Double getLastOutput() {
        if (Objects.isNull(lastOutput)) {
            lastOutput = inputable.get();
        }
        return lastOutput;
    }

    public void cleanUp() {
        lastOutput = null;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }
}
