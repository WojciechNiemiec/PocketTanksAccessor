package com.wniemiec.cheat.neural;

public abstract class InputProperty implements Inputable {

    private ValueCompressor compresser;

    public InputProperty(ValueCompressor compresser) {
        this.compresser = compresser;
    }

    @Override
    public Double get() {
        return compresser.compress(getActualValue());
    }

    protected abstract Double getActualValue();
}
