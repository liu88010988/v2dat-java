package com.v2dat;

import com.v2dat.unpack.GeoIPUnPacker;
import com.v2dat.unpack.GeoSiteUnPacker;
import com.v2dat.unpack.UnPacker;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author by liujiawei
 * @date 2024/8/8 13:09
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option unpack = new Option("u", "unpack", true, "unpack type");
        unpack.setRequired(true);
        options.addOption(unpack);

        Option outDir = new Option("d", "dir", true, "out dir");
        outDir.setRequired(true);
        options.addOption(outDir);

        Option tag = new Option("t", "tag", true, "filter tags");
        tag.setRequired(false);
        options.addOption(tag);

        Option file = new Option("f", "file", true, "data file");
        file.setRequired(true);
        options.addOption(file);

        Option clean = new Option("c", "clean", true, "clean out dir if need");
        clean.setRequired(false);
        options.addOption(clean);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            String unpackTypeVal = cmd.getOptionValue("unpack");
            String outDirVal = cmd.getOptionValue("dir");
            String fileVal = cmd.getOptionValue("file");
            String tagVal = cmd.getOptionValue("tag");
            String cleanVal = cmd.getOptionValue("clean");
            List<String> tags = new ArrayList<>();
            if (tagVal != null && !tagVal.isEmpty()) {
                tags.addAll(Arrays.asList(tagVal.split(",")));
            }
            boolean cleanFlag = !"false".equals(cleanVal);

            switch (unpackTypeVal) {
                case "geoip" -> {
                    UnPacker geoIPUnPacker = new GeoIPUnPacker();
                    geoIPUnPacker.unpack(fileVal, outDirVal, tags, cleanFlag);
                }
                case "geosite" -> {
                    UnPacker geoSiteUnPacker = new GeoSiteUnPacker();
                    geoSiteUnPacker.unpack(fileVal, outDirVal, tags, cleanFlag);
                }
                default -> {
                }
            }
        } catch (ParseException e) {
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
    }
}
