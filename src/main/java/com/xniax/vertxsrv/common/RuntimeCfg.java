package com.xniax.vertxsrv.common;

/**
 * 配置相关
 * 
 * @author wangyucheng
 *
 */
public class RuntimeCfg {

    //发号器发号需要的数据节点标识ID,取值范围0到31
    public static long datacenterId = 0;

    //发号器发号需要的机器标识ID,取值范围0到31
    public static long machineId = 0;

    //发号器发号需要的起始时间戳
    public static long startstamp = 0L;

    //服务器启动端口
    public static int port = 8888;

}
