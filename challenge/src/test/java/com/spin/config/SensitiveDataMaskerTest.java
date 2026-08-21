package com.spin.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SensitiveDataMaskerTest {

    @Test
    void keepsOnlyLastSixAccountIdCharacters() {
        assertEquals("***123456", SensitiveDataMasker.maskAccountId("acc-123456"));
    }

    @Test
    void masksNullAndShortValuesCompletely() {
        assertEquals("***", SensitiveDataMasker.maskAccountId(null));
        assertEquals("***", SensitiveDataMasker.maskAccountId("123456"));
    }
}