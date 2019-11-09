package com.study.loadbalance;

public class KeyHolder {
    private static final ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

    public static void putIP(String ip) {
        ipThreadLocal.set(ip);
    }

    public static String getIP() {
        return ipThreadLocal.get();
    }

    public static void remove() {
        //ipThreadLocal.remove();
    }
}
