package dev.davidson.ian.advent.year2016.day14;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private static final MessageDigest MD;

    static {
        try {
            MD = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String encodeToHex(final String raw, final Integer times){
        String input = raw;
        for(int i =0 ;i  < times; i++){
            input = encodeToHex(input);
        }

        return input;
    }

    private String encodeToHex(final String raw){
        byte[] digest = MD.digest(raw.getBytes(StandardCharsets.UTF_8));

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
