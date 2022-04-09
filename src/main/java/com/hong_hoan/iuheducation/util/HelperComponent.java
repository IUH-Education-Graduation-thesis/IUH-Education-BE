package com.hong_hoan.iuheducation.util;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class HelperComponent {
    public static String byPaddingZeros(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }

    // Java 9
    public static void copyInputStreamToFileJava9(InputStream input, File file)
            throws IOException {

        // append = false
        try (OutputStream output = new FileOutputStream(file, false)) {
            input.transferTo(output);
        }

    }
}
