package com.xniax.vertxsrv.handler;

import com.alibaba.fastjson.JSONObject;
import com.xniax.handler.VLogicHandler;

import io.vertx.ext.web.RoutingContext;
/**
 * 范例handler
 * @author wangyucheng
 */
public class IndexHandler implements VLogicHandler {
    
    public JSONObject index(RoutingContext rc) {
        JSONObject ret = new JSONObject();
        ret.put("desc", "index");
        return ret;
    }
}
