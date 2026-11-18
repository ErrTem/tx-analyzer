package com.example.analyzer.money;

public abstract class WalletException extends RuntimeException {
    public WalletException(String message) {
        super(message);
    }

    public WalletException(String message, Throwable cause) {
        super(message, cause);
    }

}
