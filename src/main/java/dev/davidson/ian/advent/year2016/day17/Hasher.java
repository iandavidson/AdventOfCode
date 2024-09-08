package dev.davidson.ian.advent.year2016.day17;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private static MessageDigest MD;

    private static MessageDigest getMessageDigest() {
        if (MD == null) {
            try {
                MD = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        return MD;
    }

    public static String encodeToHex(final String raw) {
        MessageDigest md = getMessageDigest();
        byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();

        for (int j = 0; j < digest.length; j++) {
            String hexChunk = Integer.toHexString(0xFF & digest[j]);
            if (hexChunk.length() == 1) {
                hexChunk = "0" + hexChunk;
            }

            hexString.append(hexChunk);
        }

        return hexString.toString();
    }
}
