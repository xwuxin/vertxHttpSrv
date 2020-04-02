package com.xniax.vertxsrv.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xniax.common.algorithm.SnowFlakeIns;
import com.xniax.vertxsrv.common.handler.BaseHttpHandler;

import ch.qos.logback.classic.util.ContextInitializer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * http server 启动入口类
 * 
 * @author wangyucheng<2751072@qq.com>
 */
public class VertxSrvApp extends AbstractVerticle {

    static {
        initLogger();
        loadConfigure();
    }

    private static Logger logger;

    public static void main(String[] args) {
        VertxSrvApp app = new VertxSrvApp();
        Vertx.vertx().deployVerticle(app);
        app.shutHook();
    }

    @Override
    public void start() {
        try {
            logger.info("RuntimeCfg.datacenterId:{}", RuntimeCfg.datacenterId);
            logger.info("RuntimeCfg.machineId:{}", RuntimeCfg.machineId);
           
            // 机器位为datacenterId和machineId各占5位，总共10位可部署在2^10=1024台机器上，取值范围在0-31之间
            SnowFlakeIns.init(RuntimeCfg.datacenterId, RuntimeCfg.machineId);
            // 创建HttpServer
            HttpServer server = vertx.createHttpServer();
            // 创建路由对象
            Router router = Router.router(vertx);
            BaseHttpHandler baseHandler = new BaseHttpHandler();
            // 监听/index地址
            router.route("/index").handler(baseHandler);
            // 把请求交给路由处理
            server.requestHandler(router);
            server.listen(RuntimeCfg.port);

            logger.info("start server ok!....");
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * 初始化log
     * 
     * @author wangyucheng
     */
    private static void initLogger() {
        if (null == System.getProperty("vertxsrv.home")) {
            System.setProperty("vertxsrv.home", System.getProperty("user.dir"));
        }
        String configFileStr = System.getProperty("vertxsrv.home") + "/config/logback.xml";
        if (null != System.getProperty("vertxsrv.config")) {
            configFileStr = System.getProperty("vertxsrv.config") + "/logback.xml";
        }
        System.getProperties().setProperty("log.home", "./");

        if (System.getProperty("log.home") != null) {
            System.getProperties().setProperty("logging.path", System.getProperty("log.home"));
        }
        System.getProperties().setProperty("logback.configurationFile", configFileStr);
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, configFileStr);
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LoggerFactory.getLogger(VertxSrvApp.class)
                    .error("Uncaught Exception in thread '" + t.getName() + "'", e);
            LoggerFactory.getLogger(VertxSrvApp.class).error(e.getMessage(), e);
        });
        logger = LoggerFactory.getLogger(VertxSrvApp.class);
    }

    /**
     * 加载configure.properties
     * 
     * @author wangyucheng
     */
    private static void loadConfigure() {
        /** 加载配置文件 */
        String cfgPath = String.format("%s/config", (String) System.getProperty("vertxsrv.home"));
        if (null != System.getProperty("vertxsrv.config")) {
            cfgPath = System.getProperty("vertxsrv.config");
        }
        Properties pro = new Properties();
        try {
            FileInputStream in = new FileInputStream(cfgPath + "/configure.properties");
            pro.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("configpath:{}", System.getProperty("user.dir"));
            logger.error(e.getMessage(), e);
        }

        // 读取IP地址
        final long datacenterId = Long.valueOf(pro.getProperty("vertxsrv.datacenterId"));
        RuntimeCfg.datacenterId = datacenterId;

        final long machineId = Long.valueOf(pro.getProperty("vertxsrv.machineId"));
        RuntimeCfg.machineId = machineId;

        final int port = Integer.valueOf(pro.getProperty("vertxsrv.port"));
        RuntimeCfg.port = port;
    }

    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        logger.info("stop server ok!");
    }

    public void shutHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.stop();
        }));
    }
}
