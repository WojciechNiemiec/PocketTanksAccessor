package com.wniemiec.cheat.neural;

public class SimpleValueCompresser implements ValueCompressor {

    private Double range;

    public SimpleValueCompresser(Double range) {
        this.range = range;
    }

    public SimpleValueCompresser(Double rangeFrom, Double rangeTo) {
        this(Math.abs(rangeTo - rangeFrom));
    }

    public Double compress(Double value) {
        return value / range;
    }

    public Double decompress(Double value) {
        return value * range;
    }

}
