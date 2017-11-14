package com.wniemiec.cheat.neural;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wojciech Niemiec
 */
public class Neuron implements Synapse {

    private static final long serialVersionUID = 4675925185667735286L;

    private List<Input> inputs = new LinkedList<>();
    private ActivationFunction activationFunction = new BipolarSigmoidalFunction();

    public Neuron() {
        inputs.add(new Input(new Bias(), Math.random()));
    }

    public Neuron(ActivationFunction activationFunction) {
        this();
        this.activationFunction = activationFunction;
    }

    @Override
    public Double get() {
        return activationFunction.apply(sumInputs());
    }

    private Double sumInputs() {
        return inputs.stream()
                .mapToDouble(Input::toDouble)
                .sum();
    }

    public void connect(Collection<? extends Synapse> synapses) {
        synapses.forEach(synapse -> inputs.add(new Input(synapse, Math.random())));
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public static class Builder {
        private ActivationFunction activationFunction;
        private Collection<Synapse> synapses = new ArrayList<>();

        public Builder activationFunction(ActivationFunction activationFunction) {
            this.activationFunction = activationFunction;
            return this;
        }

        public Builder addInputs(Collection<Synapse> synapses) {
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
