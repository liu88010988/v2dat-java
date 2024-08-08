package com.v2dat.unpack;

import com.v2dat.proto.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by liujiawei
 * @date 2024/8/8 14:08
 */
public class GeoSiteParser implements Parser {

    @Override
    public void parse(String dataPath, String outPath, List<String> tags, boolean clean) throws IOException {
        try (InputStream siteInput = new FileInputStream(dataPath)) {
            Data.GeoSiteList siteList = Data.GeoSiteList.parseFrom(siteInput);
            Map<String, List<String>> tagSiteMap = new HashMap<>();
            for (Data.GeoSite site : siteList.getEntryList()) {
                String tag = site.getCountryCode().toLowerCase();
                if (CollectionUtils.isEmpty(tags) || tags.contains(tag)) {
                    List<String> sites = tagSiteMap.computeIfAbsent(tag, k -> new ArrayList<>());
                    for (Data.Domain domain : site.getDomainList()) {
                        Data.Domain.Type type = domain.getType();
                        String prefix = switch (type) {
                            case Plain -> "keyword:";
                            case Regex -> "regexp:";
                            case Full -> "full:";
                            default -> "";
                        };
                        sites.add(String.format("%s%s", prefix, domain.getValue()));
                    }
                }
            }
            if (clean) {
                this.cleanPath(outPath);
            }
            for (String tag : tagSiteMap.keySet()) {
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(this.getFileName(outPath, tag)))) {
                    tagSiteMap.get(tag).forEach(writer::println);
                }
            }
        }
    }

    @Override
    public String getType() {
        return "geosite";
    }
}
