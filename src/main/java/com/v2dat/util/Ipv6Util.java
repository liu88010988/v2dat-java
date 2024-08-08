package com.v2dat.util;

import java.net.Inet6Address;

/**
 * @author by liujiawei
 * @date 2024/8/8 16:02
 */
public class Ipv6Util {

    public static String simplifyIpv6(Inet6Address ipv6Address) {
        byte[] addressBytes = ipv6Address.getAddress();
        int[] segments = new int[8];
        for (int i = 0; i < 8; i++) {
            segments[i] = ((addressBytes[i * 2] & 0xFF) << 8) | (addressBytes[i * 2 + 1] & 0xFF);
        }
        int longestZeroSeqStart = -1;
        int longestZeroSeqLength = 0;
        int currentZeroSeqStart = -1;
        int currentZeroSeqLength = 0;
        for (int i = 0; i < segments.length; i++) {
            if (segments[i] == 0) {
                if (currentZeroSeqStart == -1) {
                    currentZeroSeqStart = i;
                    currentZeroSeqLength = 1;
                } else {
                    currentZeroSeqLength++;
                }
            } else {
                if (currentZeroSeqLength > longestZeroSeqLength) {
                    longestZeroSeqStart = currentZeroSeqStart;
                    longestZeroSeqLength = currentZeroSeqLength;
                }
                currentZeroSeqStart = -1;
                currentZeroSeqLength = 0;
            }
        }
        if (currentZeroSeqLength > longestZeroSeqLength) {
            longestZeroSeqStart = currentZeroSeqStart;
            longestZeroSeqLength = currentZeroSeqLength;
        }
        StringBuilder simplified = new StringBuilder();
        boolean insideZeroSeq = false;
        for (int i = 0; i < segments.length; i++) {
            if (i == longestZeroSeqStart) {
                simplified.append("::");
                i += longestZeroSeqLength - 1;
                insideZeroSeq = true;
            } else {
                if (i > 0 && !insideZeroSeq) {
                    simplified.append(":");
                }
                simplified.append(Integer.toHexString(segments[i]));
                insideZeroSeq = false;
            }
        }
        if (simplified.toString().isEmpty()) {
            simplified.append("::");
        }
        return simplified.toString();
    }
}
