package com.xniax.vertxsrv.common.handler;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xniax.common.NiaStringUtils;
import com.xniax.handler.VLogicHandler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * handler基类
 * @author wangyucheng<2751072@qq.com>
 */
public class BaseHttpHandler implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(BaseHttpHandler.class);

    public static String getContr(HttpServerRequest req) {
        String c = req.getParam("c");
        if (null == c) {
            c = "index";
        }
        return NiaStringUtils.upperFirst(c);
    }

    public static String getAction(HttpServerRequest req) {
        String m = req.getParam("m");
        if (null == m) {
            m = "index";
        }
        return m;
    }

    @Override
    public void handle(RoutingContext event) {
        String c = getContr(event.request());
        String m = getAction(event.request());
        HttpServerResponse res = event.response();
        String handlerClass = String.format("com.xniax.vertxsrv.handler.%sHandler", c);
        JSONObject ret = null;
        try {
            Class<?> clz = Class.forName(handlerClass);
            VLogicHandler handler = (VLogicHandler) clz.newInstance();
            Method method = clz.getMethod(m, RoutingContext.class);
            ret = (JSONObject) method.invoke(handler, event);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        if (null == ret) {
            ret = new JSONObject();
        }
        res.end(JSON.toJSONString(ret), "UTF-8");
    }
}
