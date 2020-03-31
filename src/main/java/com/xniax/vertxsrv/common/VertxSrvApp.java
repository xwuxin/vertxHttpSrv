package com.xniax.vertxsrv.common;

import com.xniax.vertxsrv.handler.BaseHttpHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class VertxSrvApp extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new VertxSrvApp());
    }

    @Override
    public void start() {
        try {
            // 创建HttpServer
            HttpServer server = vertx.createHttpServer();
            // 创建路由对象
            Router router = Router.router(vertx);
            BaseHttpHandler baseHandler = new BaseHttpHandler();
            // 监听/index地址
            router.route("/index").handler(baseHandler);
            // 把请求交给路由处理
            server.requestHandler(router);
            server.listen(8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

}
