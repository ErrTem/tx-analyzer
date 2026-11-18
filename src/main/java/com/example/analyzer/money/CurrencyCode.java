package com.example.analyzer.money;

public enum CurrencyCode {
    RUB("₽"),
    USD("$"),
    EUR("€");

    private final String symbol;

    CurrencyCode(String currency) {
        this.symbol = currency;
    }

    public String symbol() {
        return this.symbol;
    }

    public static CurrencyCode fromCode(String code) {
        String clearStr = code.strip();
        for (CurrencyCode currency: CurrencyCode.values()) {
            if (currency.name().equalsIgnoreCase(clearStr) ) return currency;
        }

        throw new IllegalArgumentException("Currency" +  code+ "not allowed");
    }
}
