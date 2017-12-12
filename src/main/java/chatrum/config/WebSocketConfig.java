package chatrum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import chatrum.handler.WebSocketHander;

/**
 * 
 * @author Vimmone 分配链接，注入hander
 */
@Configuration
@EnableWebSocket // 开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private WebSocketHander webSocketHander;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHander, "/echo");
	}
}