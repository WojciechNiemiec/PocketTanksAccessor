package com.wniemiec.cheat.neural;

import java.io.Serializable;
import java.util.*;

public class Layer<T extends Inputable> implements Serializable, Iterator<Layer<Neuron>> {

    private static final long serialVersionUID = -8678713589097858309L;

    private List<T> inputs;
    private Layer<Neuron> next;
    private String alias;

    private Layer(Collection<T> inputs) {
        this.inputs = new ArrayList<>(inputs);
    }

    List<T> getInputs() {
        return inputs;
    }

    Layer<Neuron> connect(Layer<Neuron> layer) {
        next = layer;
        next.getInputs().forEach(neuron -> neuron.connect(this.getInputs()));

        return next;
    }

    @Override
    public boolean hasNext() {
        return Objects.nonNull(next);
    }

    @Override
    public Layer<Neuron> next() {
        return next;
    }

    public static <T extends Inputable> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends Inputable> {
        private List<T> inputs = new ArrayList<>();
        private String alias;

        public Builder<T> add(T input) {
            inputs.add(input);
            return this;
        }

        public Builder<T> alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Layer<T> build() {
            Layer<T> layer = new Layer<>(inputs);
            layer.alias = alias;
            return layer;
        }
    }
}
