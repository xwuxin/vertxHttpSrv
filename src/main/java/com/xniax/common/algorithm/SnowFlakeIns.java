package com.xniax.common.algorithm;

public class SnowFlakeIns {

    private static SnowFlake snowFlake;

    public static void init(long datacenterId, long machineId) {
        snowFlake = new SnowFlake(datacenterId, machineId);
    }

    public static SnowFlake getSnowFlake() {
        return snowFlake;
    }
}
