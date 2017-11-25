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

    private List<Synapse> inputs = new LinkedList<>();
    private List<Synapse> outputs = new LinkedList<>();
    private Double error;
    private Double propagatedValue;
    private ActivationFunction activationFunction = new BipolarSigmoidalFunction();

    public Neuron() {
        inputs.add(new Synapse(new Bias(), this, Math.random()));
    }

    public Neuron(ActivationFunction activationFunction) {
        this();
        this.activationFunction = activationFunction;
    }

    @Override
    public Double get() {
        return propagatedValue;
    }

    void doPropagation() {
        propagatedValue = activationFunction.apply(sumInputs());
    }

    void doBackPropagation() {
        error = outputs.stream()
                .mapToDouble(Synapse::getWeightedError)
                .sum();
    }

    private double sumInputs() {
        return inputs.stream()
                .mapToDouble(Synapse::getWeightedInput)
                .sum();
    }

    /**
     * Creates synapses between Neuron and inputs
     * @param inputs - elements to connect
     */
    void connect(Collection<? extends Inputable> inputs) {
        for (Inputable input : inputs) {
            Synapse synapse = new Synapse(input, this, Math.random());
            this.inputs.add(synapse);

            if (input instanceof Neuron) {
                ((Neuron)input).outputs.add(synapse);
            }
        }
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
