# vertxHttpSrv
vert.x web,vert.x实现的http server. 采用MIT开源许可
采用maven构建
server启动主类：com.xniax.vertxsrv.common.VertxSrvApp
本地了启动后，直接打开浏览器输入：
http://127.0.0.1:8888/index?c=news&m=list
可测试查看结果，以上请求的具体逻辑处理会转入NewsHandler类中的list方法处理。
