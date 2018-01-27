# javaWeb聊天室(websocket)
## 功能说明
 * 用javaweb技术实现网络聊天室，即提供多人通过文字与命令进行实时交谈、聊天的网页，并且实时显示在线用户。
## 详细
 * 核心
    > 用javaweb写聊天室，可能大多人会困惑于怎么用广播让其他人看到消息，然而http协议是短链接进行访问模式，无法像tcp协议一样建立长链接，当然大多人想到用ajax定时访问服务器获得信息的方式，可这种方式在链接过多的时候效率比较低。</br>
    > WebSocket协议是基于TCP的一种新的网络协议。它实现了浏览器与服务器`全双工(full-duplex)`通信——允许服务器主动发送信息给客户端。
 * 具体功能模块的实现在[博客](http://blog.csdn.net/qq_35442958/article/details/78925530 "CSDN博客")中有说明
 * 技术栈：spring+springMVC+websocket+maven+bootstrp
 ## 预览（pc端）
   ![Chatrum](http://img.blog.csdn.net/20171228211417962 "pc端视图")  
 ## 预览（移动端）
   ![Chatrum](http://img.blog.csdn.net/20180127164356621?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzU0NDI5NTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast "移动端视图") 
 
