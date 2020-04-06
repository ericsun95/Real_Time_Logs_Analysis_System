package com.gatech.project.domain;

import org.springframework.stereotype.Component;

@Component
public class LanguageSearchCount {
    private String name;
    private long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
