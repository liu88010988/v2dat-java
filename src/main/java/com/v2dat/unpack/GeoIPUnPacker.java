package com.v2dat.unpack;

import com.v2dat.proto.Data;
import com.v2dat.util.Ipv6Util;

import java.io.*;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by liujiawei
 * @date 2024/8/8 14:08
 */
public class GeoIPUnPacker implements UnPacker {

    @Override
    public void unpack(String dataPath, String outPath, List<String> tags, boolean clean) throws IOException {
        try (InputStream ipInput = new FileInputStream(dataPath)) {
            Data.GeoIPList ipList = Data.GeoIPList.parseFrom(ipInput);
            Map<String, List<String>> tagIpMap = new HashMap<>();
            for (Data.GeoIP ip : ipList.getEntryList()) {
                String tag = ip.getCountryCode().toLowerCase();
                if (tags == null || tags.isEmpty() || tags.contains(tag)) {
                    List<String> ips = tagIpMap.computeIfAbsent(tag, k -> new ArrayList<>());
                    for (Data.CIDR cidr : ip.getCidrList()) {
                        InetAddress inetAddress = InetAddress.getByAddress(cidr.getIp().toByteArray());
                        String ipAddress;
                        if (inetAddress instanceof Inet6Address ipv6Address) {
                            ipAddress = Ipv6Util.simplifyIpv6(ipv6Address);
                        } else {
                            ipAddress = inetAddress.getHostAddress();
                        }
                        ips.add(String.format("%s/%s", ipAddress, cidr.getPrefix()));
                    }
                }
            }
            if (clean) {
                this.cleanPath(outPath);
            }
            for (String tag : tagIpMap.keySet()) {
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(this.getFileName(outPath, tag)))) {
                    tagIpMap.get(tag).forEach(writer::println);
                }
            }
        }
    }

    @Override
    public String getFilePrefix() {
        return "geoip";
    }
}
