package chatrum.handler;

import org.apache.log4j.Logger;
import org.aspectj.bridge.Message;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhuqiang on 2015/6/22 0022.
 */
public class WebSocketHander implements WebSocketHandler {
    private static final Logger logger = Logger.getLogger(WebSocketHander.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
    private static final ArrayList<TextMessage> textMessage= new ArrayList<TextMessage>();
	private HashMap<WebSocketSession,TextMessage> map=new HashMap<WebSocketSession,TextMessage>();
//    private HashSet<WebSocketSession> set= new HashSet<WebSocketSession>();
    //初次链接成功执行
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("链接成功......");
        users.add(session);
//        String userName = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
//        if(userName!= null){
//            //查询未读消息
//            int count = 5;
//            session.sendMessage(new TextMessage(count + ""));
//        }
        for(TextMessage value:map.values()) {
       	 String a = "@"+value.getPayload();
            TextMessage bb=new TextMessage(a);
              session.sendMessage(bb);
       }
        for (TextMessage textMessage2 : textMessage) {
        	String a = "!"+textMessage2.getPayload();
            TextMessage bb=new TextMessage(a);
        	session.sendMessage(bb);
		}
        
    }

    //接受消息处理消息
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    	
    	textMessage.add(new TextMessage(webSocketMessage.getPayload() + ""));
//    	System.out.println(webSocketMessage.getPayload());
    	TextMessage put = map.put(webSocketSession, new TextMessage(webSocketMessage.getPayload() + "" ));
    	sendMessageToUsers(webSocketSession,new TextMessage(webSocketMessage.getPayload() + ""));
    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.debug("链接出错，关闭链接......");
        String a = map.get(webSocketSession).getPayload();
        TextMessage bb=new TextMessage("0"+a);
          sendMessageToUsers(webSocketSession,bb);
          String b=(String) a.subSequence(0, 1);
          String c=(String) a.subSequence(1, 1+Integer.parseInt(b));
          System.out.println(c);
          c=b+c+"大家再见,我下线了!";
          textMessage.add(new TextMessage(c));
        users.remove(webSocketSession);
        map.remove(webSocketSession);
    }

    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug("链接关闭......" + closeStatus.toString());
//        System.out.println(map.get(webSocketSession));
       String a = map.get(webSocketSession).getPayload();
      TextMessage bb=new TextMessage("0"+a);
        sendMessageToUsers(webSocketSession,bb);
        String b=(String) a.subSequence(0, 1);
        String c=(String) a.subSequence(1, 1+Integer.parseInt(b));
        System.out.println(c);
        c=b+c+"大家再见,我下线了!";
        textMessage.add(new TextMessage(c));
        users.remove(webSocketSession);
        map.remove(webSocketSession);
    }

    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 给所有在线用户发送消息
     * @param webSocketSession 
     *
     * @param message
     */
    public void sendMessageToUsers(WebSocketSession webSocketSession, TextMessage message) {
    	
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}