package chatrum.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import chatrum.handler.HandshakeInterceptor;
import chatrum.handler.WebSocketHander;

/**
* Created by zhuqiang on 2015/6/23 0023.
        */
@Configuration
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHander(),"/echo").addInterceptors(
        		new HandshakeInterceptor()); //支持websocket 的访问链接
        registry.addHandler(new WebSocketHander(),"/sockjs/echo").addInterceptors(
        		new HandshakeInterceptor()).withSockJS(); //不支持websocket的访问链接
    }
}