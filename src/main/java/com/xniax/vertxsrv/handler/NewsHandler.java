package com.xniax.vertxsrv.handler;

import com.alibaba.fastjson.JSONObject;
import com.xniax.handler.VLogicHandler;

import io.vertx.ext.web.RoutingContext;

/**
 * 范例handler
 * @author wangyucheng
 *
 */
public class NewsHandler implements VLogicHandler{
    public JSONObject list(RoutingContext rc) {
        JSONObject ret = new JSONObject();
        ret.put("desc", "new list");
        return ret;
    }
}
