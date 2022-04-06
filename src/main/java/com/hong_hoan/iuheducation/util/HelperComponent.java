package com.hong_hoan.iuheducation.util;

import org.springframework.stereotype.Component;

@Component
public class HelperComponent {
    public static String byPaddingZeros(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }
}
