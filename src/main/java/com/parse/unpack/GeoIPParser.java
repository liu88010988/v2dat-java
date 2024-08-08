package com.parse.unpack;

import com.parse.proto.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by liujiawei
 * @date 2024/8/8 14:08
 */
public class GeoIPParser implements Parser {

    public void parse(String dataPath, String outPath, List<String> tags, boolean clean) throws IOException {
        try (InputStream ipInput = new FileInputStream(dataPath)) {
            Data.GeoIPList ipList = Data.GeoIPList.parseFrom(ipInput);
            Map<String, List<String>> tagIpMap = new HashMap<>();
            for (Data.GeoIP ip : ipList.getEntryList()) {
                String tag = ip.getCountryCode().toLowerCase();
                if (CollectionUtils.isEmpty(tags) || tags.contains(tag)) {
                    List<String> ips = tagIpMap.computeIfAbsent(tag, k -> new ArrayList<>());
                    for (Data.CIDR cidr : ip.getCidrList()) {
                        InetAddress inetAddress = InetAddress.getByAddress(cidr.getIp().toByteArray());
                        ips.add(String.format("%s/%s", inetAddress.getHostAddress(), cidr.getPrefix()));
                    }
                }
            }
            if (clean) {
                this.cleanPath(outPath);
            }
            for (String tag : tagIpMap.keySet()) {
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(outPath + "/" + tag + ".txt"))) {
                    tagIpMap.get(tag).forEach(writer::println);
                }
            }
        }
    }
}
