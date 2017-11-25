package com.wniemiec.cheat.neural;

public abstract class InputProperty implements Inputable {

    private ValueCompresser compresser;

    public InputProperty(ValueCompresser compresser) {
        this.compresser = compresser;
    }

    @Override
    public Double get() {
        return compresser.compress(getActualValue());
    }

    protected abstract Double getActualValue();
}
