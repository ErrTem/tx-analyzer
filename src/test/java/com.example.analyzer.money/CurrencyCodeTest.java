package com.example.analyzer.money;


import org.junit.Test;

import static com.example.analyzer.money.CurrencyCode.USD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CurrencyCodeTest {

    @Test
    void fromCodeTransform() {
        assertThat(CurrencyCode.fromCode("   usd   ")).isEqualTo(USD);

    }

    @Test
    void wrongCodeFormUnvalidArg() {
        assertThatThrownBy(() -> CurrencyCode.fromCode("   eblo   "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
