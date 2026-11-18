package com.example.analyzer.money;

public class CurrencyMismatchException extends WalletException {
    public CurrencyMismatchException(String message) {
        super(message);
    }

    public CurrencyMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
