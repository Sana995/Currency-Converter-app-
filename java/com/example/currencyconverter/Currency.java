package com.example.currencyconverter;

public class Currency {

    private String currency_code;
    private int unit_value;
    private double buying_rate;
    private double selling_rate;
    private double  median_rate;

    public Currency(String currency_code, int unit_value, double buying_rate, double selling_rate, double median_rate) {
        this.currency_code = currency_code;
        this.unit_value = unit_value;
        this.buying_rate = buying_rate;
        this.selling_rate = selling_rate;
        this.median_rate = median_rate;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public int getUnit_value() {
        return unit_value;
    }

    public double getBuying_rate() {
        return buying_rate;
    }

    public double getSelling_rate() {
        return selling_rate;
    }

    public double getMedian_rate() {
        return median_rate;
    }
}
