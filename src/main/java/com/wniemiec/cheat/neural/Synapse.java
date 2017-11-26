package com.wniemiec.cheat.neural;

/**
 * This class represents a weighted connection between two elements in neural Network
 */
public class Synapse {
    private Inputable input;
    private Neuron output;
    private Double weight;

    Synapse(Inputable input, Neuron output, Double weight) {
        this.input = input;
        this.output = output;
        this.weight = weight;
    }

    Double getInput() {
        return input.get();
    }

    Double getWeightedInput() {
        return input.get() * weight;
    }

    Double getWeightedError() {
        return output.getError() * weight;
    }

    Double getWeight() {
        return weight;
    }

    void setWeight(Double weight) {
        this.weight = weight;
    }
}
