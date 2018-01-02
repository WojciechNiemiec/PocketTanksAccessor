package com.wniemiec.cheat.neural;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wojciech Niemiec
 */
public class Neuron implements Inputable {
    private static final long serialVersionUID = 4675925185667735286L;

    private final static Double BIAS = 1.0;
    private final static Double RO = 0.2;
    private final static Double ALPHA = 0.000009;

    private Synapse bias = new Synapse(() -> BIAS, this, Math.random());
    private List<Synapse> inputs = new LinkedList<>();
    private List<Synapse> outputs = new LinkedList<>();
    private Double error;
    private Double activatedValue;
    private ActivationFunction activationFunction;

    public Neuron() {
        activationFunction = new BipolarSigmoidFunction();
    }

    public Neuron(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    @Override
    public Double get() {
        return activatedValue;
    }

    void doPropagation() {
        activatedValue = activationFunction.apply(sumInputs());
    }

    public void doBackPropagation(Double propagatedError) {
        Double activatedDerivative = activationFunction.applyDerivative(activatedValue);

        error = activatedDerivative * propagatedError;
    }

    void doBackPropagation() {
        doBackPropagation(getWeightedErrorSum());
    }

    private double getWeightedErrorSum() {
        return outputs.stream()
                .mapToDouble(Synapse::getWeightedError)
                .sum();
    }

    void updateInputWeights() {
        updateWeight(bias);
        inputs.forEach(this::updateWeight);
    }

    private void updateWeight(Synapse synapse) {
        Double oldWeight = synapse.getWeight();
        Double newWeight = oldWeight + ALPHA * oldWeight + (RO * error * synapse.getInput());
        synapse.setWeight(newWeight);
    }

    private double sumInputs() {
        return bias.getWeightedInput() + inputs.stream()
                .mapToDouble(Synapse::getWeightedInput)
                .sum();
    }

    /**
     * Creates synapses between Neuron and inputs
     * @param inputs - elements to connect
     */
    void connect(Collection<? extends Inputable> inputs) {
        for (Inputable input : inputs) {
            Synapse synapse = new Synapse(input, this, getRandomWeight());
            this.inputs.add(synapse);

            if (input instanceof Neuron) {
                ((Neuron)input).outputs.add(synapse);
            }
        }
    }

    private double getRandomWeight() {
        double weight = Math.random() - 0.5;
        return (-0.05 > weight || weight > 0.05) ? weight : 0.1;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }


    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ActivationFunction activationFunction;
        private Collection<Inputable> synapses = new ArrayList<>();

        public Builder activationFunction(ActivationFunction activationFunction) {
            this.activationFunction = activationFunction;
            return this;
        }

        public Builder addInputs(Collection<Inputable> synapses) {
            this.synapses.addAll(synapses);
            return this;
        }

        public Neuron build() {
            Neuron neuron = new Neuron(activationFunction);
            neuron.connect(synapses);
            return neuron;
        }
    }
}
