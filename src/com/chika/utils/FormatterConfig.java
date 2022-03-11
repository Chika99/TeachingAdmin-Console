package com.chika.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cheng Liu
 */
public class FormatterConfig {
    private String title;
    private List<?> contents;
    private int placeholder;
    private boolean hasNext;
    private int index;
    private int listIndex;
    private String extra;


    public FormatterConfig(String title, List<?> contents, int placeholder) {
        this.title = title;
        this.contents = contents;
        this.placeholder = placeholder;
    }

    public String getTitle() {
        return title;
    }

    public List<?> getContents() {
        return contents;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getListIndex() {
        return listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public void clearCache() {
        hasNext = false;
        index = 0;
        listIndex = 0;
        extra = null;
    }


    public static class Builder {
        private String title;
        private List<?> contents;
        private int placeholder;

        public Builder() {
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContents(int content) {
            List<Integer> integers = new ArrayList<>();
            integers.add(content);
            setContents(integers);
            return this;
        }

        public Builder setContents(String content) {
            List<String> strings = new ArrayList<>();
            strings.add(content);
            setContents(strings);
            return this;
        }


        public Builder setContents(List<?> contents) {
            this.contents = contents;
            return this;
        }

        public Builder setPlaceholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public FormatterConfig build() {
            return new FormatterConfig(title, contents, placeholder);
        }
    }
}
