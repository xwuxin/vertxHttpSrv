package com.xniax.vertxsrv.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.xniax.common.algorithm.SnowFlakeIns;
import com.xniax.handler.VLogicHandler;
import com.xniax.vertxsrv.common.VertxSrvApp;

import io.vertx.ext.web.RoutingContext;

/**
 * 范例handler
 * 
 * @author wangyucheng
 */
public class IndexHandler implements VLogicHandler {

    private Logger logger = LoggerFactory.getLogger(VertxSrvApp.class);
    
    public JSONObject index(RoutingContext rc) {
        JSONObject ret = new JSONObject();
        ret.put("desc", "index");
        return ret;
    }

    public JSONObject snowflake(RoutingContext rc) {
        JSONObject ret = new JSONObject();
        ret.put("id", SnowFlakeIns.getSnowFlake().nextId());
        logger.info("id:"+ret.getString("id"));
        return ret;
    }
}
