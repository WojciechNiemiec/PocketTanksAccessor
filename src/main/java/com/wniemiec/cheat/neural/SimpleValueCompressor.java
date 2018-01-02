package com.wniemiec.cheat.neural;

public class SimpleValueCompressor implements ValueCompressor {

    private Double range;

    public SimpleValueCompressor(Double range) {
        this.range = range;
    }

    public SimpleValueCompressor(Double rangeFrom, Double rangeTo) {
        this(Math.abs(rangeTo - rangeFrom));
    }

    public Double compress(Double value) {
        return value / range;
    }

    public Double decompress(Double value) {
        return value * range;
    }

}
