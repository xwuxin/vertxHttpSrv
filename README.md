# vertxHttpSrv

基于vert.x实现的http server.

内置一个发号器，采用SnowFlake算法。

编号单调递增,时间回拨会拒绝生成id。

项目用maven构建

server启动主类：com.xniax.vertxsrv.common.VertxSrvApp

本地了启动后，直接打开浏览器输入：

http://127.0.0.1:8888/index?c=index&m=snowflake

可测试查看结果.
