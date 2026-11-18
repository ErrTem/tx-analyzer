package com.example.analyzer.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount, CurrencyCode currency) {
    public Money {
        Objects.requireNonNull(amount, "amount can not be null!");
        Objects.requireNonNull(currency, "currency can not be null!");

        try {
            amount = amount.setScale(2, RoundingMode.UNNECESSARY);

        } catch (ArithmeticException e) {
            throw new InvalidAmountException("..." + amount, e);
        }
    }

    public static Money of(String amount, CurrencyCode currency) {
        try {
            BigDecimal decimal = new BigDecimal(amount.strip().replace(",", "."));
            return new Money(decimal, currency);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("received" + amount, e);
        }
    }

    public static Money zero(CurrencyCode currency) {
        return Money.of("0", currency);
    }

    public Money plus(Money other) {
        if (other.currency.equals(this.currency)) {
            BigDecimal sum = amount.add(other.amount);
            return new Money(sum, currency);

        } else {
            throw new CurrencyMismatchException(other + "could not execute plus for" + currency);
        }
    }

    public Money minus(Money other) {
        if (other.currency.equals(this.currency)) {
            BigDecimal sum = amount.subtract(other.amount);
            return new Money(sum, currency);

        } else {
            throw new CurrencyMismatchException(other + "could not execute minus for" + currency);
        }
    }

    public Money percent(String percent) {
        if (percent == null || percent.trim().isEmpty()) {
            throw new IllegalArgumentException("String can not be empty");
        }
        String clean = percent.replace("%", "").trim();
        try {
            BigDecimal val = new BigDecimal(clean);
            val = val.movePointLeft(2).multiply(this.amount).setScale(2, RoundingMode.HALF_UP);
            return new Money(val, currency);
        } catch (InvalidAmountException e) {
            throw new InvalidAmountException("can not create BigNumber from" + percent, e);
        }
    }

    public boolean isPositive() {
        return this.amount.signum() == 1;
    }

    public boolean isNegative() {
        return this.amount.signum() == -1;
    }

    public boolean isGreaterThan(Money cash) {
        if (!cash.currency.equals(currency)) {
            throw new CurrencyMismatchException(cash.currency + "doesnt match" + currency);
        }
        return this.amount.compareTo(cash.amount) > 0;
    }

    public boolean isLessThan(Money cash) {
        if (!cash.currency.equals(currency)) {
            throw new CurrencyMismatchException(cash.currency + "doesnt match" + currency);
        }
        return this.amount.compareTo(cash.amount) < 0;
    }

    public String format() {
        return amount.toPlainString() + " " + currency.symbol();
    }
}
