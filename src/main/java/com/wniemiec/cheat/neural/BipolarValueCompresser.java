package com.wniemiec.cheat.neural;

public class BipolarValueCompresser implements ValueCompresser {

    private Double halfRange;

    public BipolarValueCompresser(Double range) {
        this.halfRange = range / 2;
    }

    BipolarValueCompresser(Double rangeFrom, Double rangeTo) {
        this(Math.abs(rangeTo - rangeFrom));
    }

    public Double compress(Double value) {
        return value / halfRange - 1;
    }

    public Double decompress(Double value) {
        return (value + 1) * halfRange;
    }

}
