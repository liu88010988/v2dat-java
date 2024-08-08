package com.v2dat;

import com.v2dat.unpack.GeoIPParser;
import com.v2dat.unpack.GeoSiteParser;
import com.v2dat.unpack.Parser;

import java.util.List;

/**
 * @author by liujiawei
 * @date 2024/8/8 13:09
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // GeoIPParser test case
        Parser geoIPParser = new GeoIPParser();
        geoIPParser.parse("/Users/liujiawei/Documents/github/gfw2dnsmasq/ip/geoip.dat", "/Users/liujiawei/Desktop/geo/geoip", List.of("cn"), true);

        // GeoSiteParser test case
        Parser geoSiteParser = new GeoSiteParser();
        geoSiteParser.parse("/Users/liujiawei/Documents/github/gfw2dnsmasq/domain/geosite.dat", "/Users/liujiawei/Desktop/geo/geosite", List.of("gfw"), true);
    }
}
