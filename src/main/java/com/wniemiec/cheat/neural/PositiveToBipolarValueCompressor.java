package com.wniemiec.cheat.neural;

public class PositiveToBipolarValueCompressor implements ValueCompressor {

    private Double halfRange;

    public PositiveToBipolarValueCompressor(Double range) {
        this.halfRange = range / 2;
    }

    public PositiveToBipolarValueCompressor(Double rangeFrom, Double rangeTo) {
        this(Math.abs(rangeTo - rangeFrom));
    }

    public Double compress(Double value) {
        return value / halfRange - 1;
    }

    public Double decompress(Double value) {
        return (value + 1) * halfRange;
    }

}
