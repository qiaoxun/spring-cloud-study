package com.study.distribute.lock.redis.utils;

public class LockConstants {
    public static final String OK = "OK";

    // NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
    public static final String NOT_EXISTS = "NX";
    public static final String EXISTS = "XX";

    /** expx EX|PX, expire time units: EX = seconds; PX = milliseconds **/
    public static final String SECONDS = "EX";
    public static final String MILLISECONDS = "PX";

    private LockConstants(){}

}
