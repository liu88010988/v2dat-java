package com.v2dat;

import com.v2dat.unpack.GeoIPUnPacker;
import com.v2dat.unpack.GeoSiteUnPacker;
import com.v2dat.unpack.UnPacker;

import java.util.List;

/**
 * @author by liujiawei
 * @date 2024/8/8 13:09
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // GeoIPUnPacker test case
        UnPacker geoIPUnPacker = new GeoIPUnPacker();
        geoIPUnPacker.unpack("/Users/liujiawei/Documents/github/gfw2dnsmasq/ip/geoip.dat", "/Users/liujiawei/Desktop/geo/geoip", List.of("cn"), true);

        // GeoSiteUnPacker test case
        UnPacker geoSiteUnPacker = new GeoSiteUnPacker();
        geoSiteUnPacker.unpack("/Users/liujiawei/Documents/github/gfw2dnsmasq/domain/geosite.dat", "/Users/liujiawei/Desktop/geo/geosite", List.of("gfw"), true);
    }
}
