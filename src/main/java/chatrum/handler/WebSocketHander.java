package chatrum.handler;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import chatrum.service.UserHandler;

/**
 * 
 * @author vimmone 消息处理中心
 *
 */
@Service
public class WebSocketHander implements WebSocketHandler {
	@Autowired
	private UserHandler userHandler;
	private static final Logger logger = Logger.getLogger(WebSocketHander.class);

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("链接成功......");
		userHandler.addUser(session);

	}

	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
			throws Exception {
		userHandler.handleMessage(webSocketSession, webSocketMessage);

	}

	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		logger.debug("链接出错，关闭链接......");
		userHandler.error(webSocketSession);

	}

	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		logger.debug("链接关闭......" + closeStatus.toString());
		userHandler.error(webSocketSession);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

}