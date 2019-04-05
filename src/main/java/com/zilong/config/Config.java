package com.zilong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Application Config Class
 *
 * Default value be defined in application.properties
 *
 */
@Service
public class Config {
    @Value("${algorithm.k}")
    private int k;

    @Value("${algorithm.window}")
    private long window;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }
}
