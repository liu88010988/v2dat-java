package com.parse;

import com.parse.unpack.GeoIPParser;
import com.parse.unpack.GeoSiteParser;
import com.parse.unpack.Parser;

import java.util.List;

/**
 * @author by liujiawei
 * @date 2024/8/8 13:09
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Parser geoIPParser = new GeoIPParser();
        geoIPParser.parse("/Users/liujiawei/Documents/github/gfw2dnsmasq/ip/geoip.dat", "/Users/liujiawei/Desktop/geo/geoip", List.of("cn"), true);

        Parser geoSiteParser = new GeoSiteParser();
        geoSiteParser.parse("/Users/liujiawei/Documents/github/gfw2dnsmasq/domain/geosite.dat", "/Users/liujiawei/Desktop/geo/geosite", List.of("gfw"), true);
    }
}
