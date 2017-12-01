package chatrum.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chatrum.handler.WebSocketHander;

@RequestMapping("ChatController")
@Controller
public class ChatController {
	
	@RequestMapping("test")
	public void test(HttpServletResponse response) throws Exception {
		System.out.println("#");
		WebSocketHander webSocketHander = new WebSocketHander();
		webSocketHander.handleMessage(null, null);
	}
}
