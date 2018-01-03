package com.wniemiec.cheat.neural;

/**
 * This class represents a weighted connection between two elements in neural Network
 */
public class Synapse {
    private Inputable input;
    private Neuron output;
    private double oldWeight;
    private double weight;

    Synapse(Inputable input, Neuron output, double weight) {
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

    double getOldWeight() {
        return oldWeight;
    }

    void setOldWeight(double oldWeight) {
        this.oldWeight = oldWeight;
    }

    double getWeight() {
        return weight;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }
}
