package chatrum.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 
 * @author Vimmone 处理用户与消息模块
 */
@Component
public class UserHandler {
	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
	private static final ArrayList<TextMessage> textMessage = new ArrayList<TextMessage>();
	private HashMap<WebSocketSession, String> sessionName = new HashMap<WebSocketSession, String>();

	public void addUser(WebSocketSession session) throws IOException {
		users.add(session);
		for (TextMessage message : textMessage) {
			/**
			 * 新用户登陆，发送全部历史信息给该用户
			 */
			session.sendMessage(message);
		}
		for (WebSocketSession onlineSession : sessionName.keySet()) {
			/**
			 * 用户刚上线，发送全部在线用户名字，让前端维护
			 */
			session.sendMessage(new TextMessage("a2" + sessionName.get(onlineSession)));
		}

	}

	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
			throws IOException {
		// TODO Auto-generated method stub
		String message = webSocketMessage.getPayload() + "";
		String request = message.substring(0, 1);// 自定义密钥，判断请求内容
		if (request.equals("a")) { // 链接成功获得在线用户的名字
			String name = message.substring(1);// 获得名字
			sendMessageToUsers(new TextMessage("a2" + name));// 发送给前端维护在线人数列表
			String begin = name.length() + name + "大家好，我是" + name + ",请多多关照";
			textMessage.add(new TextMessage(begin));// 加入历史信息记录
			sendMessageToUsers(new TextMessage(begin));// 开场白
			sessionName.put(webSocketSession, name);
			return;// 链接后获得开头语后结束
		}
		textMessage.add(new TextMessage(message));
		sendMessageToUsers(new TextMessage(message));
	}

	public void sendMessageToUsers(TextMessage message) throws IOException {
		/**
		 * 发送消息给全部在线用户
		 */
		for (WebSocketSession user : users) {
			if (user.isOpen()) {// 判断是否异常链接
				user.sendMessage(message);
			}
		}
	}

	public void error(WebSocketSession session) throws IOException {
		/**
		 * 用户异常处理
		 */
		users.remove(session);// 先移除队列
		String name = sessionName.get(session);// 获得异常用户的名字
		sendMessageToUsers(new TextMessage("a1" + name));// 发送给前端维护在线人数列表
		String end = name.length() + name + "我先下线了，大家再见！";// 结束语句
		textMessage.add(new TextMessage(end));// 加入历史信息记录
		sendMessageToUsers(new TextMessage(end));// 结束语
		sessionName.remove(session);// 删除该用户

	}
}
