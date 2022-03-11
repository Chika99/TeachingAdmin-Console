package com.chika.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Cheng Liu
 */
public class ConsoleFormatter {
    // make sure every entity in configs.content has the same length
    public static void print(FormatterConfig[] configs) {
        boolean first = true;
        List<String> tableString = new ArrayList<>();
        // add headers
        tableString.add(""); // fixed
        tableString.add(""); // header formatter

        for (FormatterConfig config : configs) {
            String header1 = tableString.get(0);
            String contentFormatter = tableString.get(1);
            if (first) {
                header1 += "+";
                contentFormatter += "|";
                first = false;
            }
            header1 += (String.join("", Collections.nCopies(config.getPlaceholder() + 2, "-")) + "+");
            contentFormatter += (String.format(" %%-%ds |", config.getPlaceholder()));
            tableString.set(0, header1);
            tableString.set(1, contentFormatter);
        }
        System.out.println(tableString.get(0));
        System.out.printf((tableString.get(1)) + "%n", Arrays.stream(configs).map(FormatterConfig::getTitle).toArray());
        System.out.println(tableString.get(0));
        for (int i = 0; i < configs[0].getContents().size(); i++) {
            int f = i;
            boolean nextLine;
            do {
                System.out.printf((tableString.get(1)) + "%n", Arrays.stream(configs).map(config -> {
                    Object object = config.getContents().get(f);
                    String s;
                    // extra priority
                    if (config.getExtra() != null) {
                        s = config.getExtra();
                    } else {
                        if (object == null) {
                            s = "null";
                        } else {
                            if (object instanceof List) {
                                List list = ((List) object);
                                if (list.size() == 0) {
                                    if (config.getListIndex() > 0) {
                                        s = "";
                                    } else {
                                        s = "empty";
                                    }
                                } else {
                                    if (config.getListIndex() > list.size() - 1) {
                                        s = "";
                                    } else {
                                        s = list.get(config.getListIndex()).toString();
                                        config.setListIndex(config.getListIndex() + 1);
                                        // stop loop
                                        config.setHasNext(config.getListIndex() != list.size());
                                    }
                                }
                            } else {
                                // is not list but need next line, return blank
                                if (!config.isHasNext() && config.getIndex() > 0) {
                                    s = "";
                                } else {
                                    s = object.toString();
                                }
                            }
                        }
                    }
                    config.setIndex(config.getIndex() + 1);
                    if (s.length() > config.getPlaceholder()) {
                        config.setExtra(s.substring(config.getPlaceholder()));
                        s = s.substring(0, config.getPlaceholder());
                    } else {
                        // if enough, set extra null
                        config.setExtra(null);
                    }
                    return s;
                }).toArray());
                nextLine = Arrays.stream(configs).anyMatch(config -> config.isHasNext() || (config.getExtra() != null));
            } while (nextLine);
            Arrays.stream(configs).forEach(FormatterConfig::clearCache);
            System.out.println(tableString.get(0));
        }
    }

    public static void main(String[] args) {
        FormatterConfig config = new FormatterConfig.Builder().setTitle("title1").setPlaceholder(10).build();
        FormatterConfig config1 = new FormatterConfig.Builder().setTitle("title2").setPlaceholder(10).build();
        print(new FormatterConfig[]{config, config1});
    }
}
