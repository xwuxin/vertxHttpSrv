package com.xniax.vertxsrv.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xniax.common.NiaStringUtils;
import com.xniax.handler.VLogicHandler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class BaseHttpHandler implements Handler<RoutingContext> {

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
        JSONObject ret = new JSONObject();
        try {
            Class<?> clz = Class.forName(handlerClass);
            VLogicHandler handler = (VLogicHandler) clz.newInstance();
            Method method = clz.getMethod(m, RoutingContext.class);
            ret = (JSONObject) method.invoke(handler, event);
        } catch (ClassNotFoundException e) {
            res.end("error controller :" + c);
        } catch (InstantiationException e) {
            res.end("error controller :" + e.getMessage());
        } catch (IllegalAccessException e) {
            res.end("error controller :" + e.getMessage());
        } catch (NoSuchMethodException e) {
            res.end("error method :" + e.getMessage());
        } catch (SecurityException e) {
            res.end("error method :" + e.getMessage());
        } catch (IllegalArgumentException e) {
            res.end("error method :" + e.getMessage());
        } catch (InvocationTargetException e) {
            res.end("error method :" + e.getMessage());
        }
        res.end(JSON.toJSONString(ret),"UTF-8");
    }
}
