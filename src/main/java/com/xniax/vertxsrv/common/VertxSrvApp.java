package com.xniax.vertxsrv.common;

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
     // 创建HttpServer
        HttpServer server = vertx.createHttpServer();
 
        // 创建路由对象
        Router router = Router.router(vertx);
 
        // 监听/index地址
        router.route("/index").handler(request -> {
            request.response().end("INDEX SUCCESS");
        });
 
        // 把请求交给路由处理--------------------(1)
        server.requestHandler(router::accept);
        server.listen(8888);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

}
