package com.spin.config;

public final class SensitiveDataMasker {

    private static final int VISIBLE_ACCOUNT_ID_DIGITS = 6;

    private SensitiveDataMasker() {
    }

    public static String maskAccountId(String value) {
        if (value == null || value.length() <= VISIBLE_ACCOUNT_ID_DIGITS) {
            return "***";
        }
        return "***" + value.substring(value.length() - VISIBLE_ACCOUNT_ID_DIGITS);
    }
}