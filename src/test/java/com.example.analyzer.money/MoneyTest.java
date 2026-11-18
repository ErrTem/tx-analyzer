package com.example.analyzer.money;

import org.junit.jupiter.api.Test;

import static com.example.analyzer.money.CurrencyCode.EUR;
import static com.example.analyzer.money.CurrencyCode.RUB;
import static com.example.analyzer.money.CurrencyCode.USD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MoneyTest {

    @Test
    void amountWithDifferentScaleAreEqual() {
        assertThat(Money.of("10.5", RUB)).isEqualTo(Money.of("10.50", RUB));
    }

    @Test
    void rejectMoreThanTwoDecimalPlaces() {
        assertThatThrownBy(()->Money.of("10.005", RUB))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("10.005")
                .hasCauseInstanceOf(ArithmeticException.class);
    }

    @Test
    void percentReturnsCorrectValues() {
        assertThat(Money.of("123.45", EUR).percent("  1  %  ")).isEqualTo(Money.of("1.23", EUR));
        assertThat(Money.of("12345.67", USD).percent("  0.5  %  ")).isEqualTo(Money.of("61.73", USD));
    }

    @Test
    void isGreaterThanWorks() {
        assertThat(Money.of("125", EUR).isGreaterThan(Money.of("12.5", EUR))).isTrue();
    }

    @Test
    void rejectDifferentCurrency() {
        assertThatThrownBy(()->Money.of("10.05", RUB).isLessThan(Money.of("9.05", EUR)))
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessageContaining("RUB");
    }

    @Test
    void wrongStringDoesntCreateMoney() {
        assertThatThrownBy(()->Money.of("cash", RUB))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("cash")
                .hasCauseInstanceOf(NumberFormatException.class);
    }

    @Test
    void wrongSummOfDifferentCurencies() {
        assertThatThrownBy(()->Money.of("125", EUR).plus(Money.of("125", USD)))
                .isInstanceOf(CurrencyMismatchException.class);
    }

    @Test
    void returnFormattedStringFromMoney() {
        assertThat(Money.of("-22.88", USD).format()).isEqualTo("-22.88 $");
    }
}
