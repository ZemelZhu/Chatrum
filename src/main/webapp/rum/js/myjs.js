function webSocketSession(){
	var websocket;
	var Onlineset = new Set();//维护在线人数的容器
	var mydate = new Date();//时间对象，用于显示时间
	websocket = new WebSocket("ws://127.0.0.1:8080/echo");
	websocket.onopen = function (evnt) {
		var name=$("#username").text();
		websocket.send("a"+name);//发送名字给全部在线用户，加入队列中
	};

	websocket.onmessage = function (evnt) {
		var data=evnt.data;
		//维护在线用户模块
		if(data[0]=="a") {//用户维护在线用户队列
			var name=data.slice(2);//获得名字
			if(data[1]=="1") {//用户下线，维护队列
				Onlineset.delete(name);
			}
			else {//用户上线，维护队列
				Onlineset.add(name);
			}
			 // 维护用户列表
			 var listnameset="<div class='list-group-item active'>"+
			 "<h4 class='list-group-item-heading'>聊天室成员</h4></div>";
			 $("#userlist").html(listnameset);
			 Onlineset.forEach(function (item) {
			 	if(item.toString()!=""){
			 		var content = "<div class='list-group-item '>"+
		 			"<h4 class='list-group-item-heading'>"+
		 			"<span class='glyphicon glyphicon-user'></span >"+item.toString()+"</h4></div>";

			 		$("#userlist").append(content);
		 			//添加在线用户到列表中
	 			}
		 	});
			//维护在线用户结束
			return ;
		}
		//维护消息发送模块
		//解析data，获得名字与内容
		var namelenth=parseInt(data[0]); //获得名字的长度
		var name=data.slice(1, 1+namelenth);//获得名字
		var content=data.slice(1+namelenth); //获得消息内容
		var style = "alert-success";//其他人消息样式
		var image = "other";
		if(name==$("#username").text()) {
			//消息来自自己，改变样式
			style="alert-info";
			image="myself";
		}
		var message="<li class='meida alert "+style+" ' role='alert'><div class='media-left'><img src='./image/"+image+".jpg' width='30px'"+
			"style='margin-top: 40%' alt='' class='media-object img-circle '>"+
			"</div><div class='media-body '><h6 class='meida-heading text-warning '>"+name+
			"&nbsp;&nbsp;"+mydate.toLocaleString()+"</h6>"+"<p ><xmp>"+content+"</xmp></p></div></li>";

		$("#messageList").prepend(message);

	};


	websocket.onerror = function (evnt) {
	};
	websocket.onclose = function (evnt) {
	};
	$("#sendMessage").bind("click", function() {
		if($("#chat").val()=="") {//禁止发送非空内容
			return false;
		}
		var name=$("#username").text();
		var a=name.length+name+$("#chat").val();
		websocket.send(a);
        $("#chat").val("");//发送消息后清空输入框
        $("#navbar-collapse").collapse('hide');//手机响应式下拉菜单隐藏
	});
	$('#chat').bind('keypress',function(event){
		if($("#chat").val()=="") {//禁止发送非空内容
			return false;
		}
        if(event.keyCode == "13")//回车提交
        {
            var name=$("#username").text();
			var a=name.length+name+$("#chat").val();
	        $("#chat").val("");//发送消息后清空输入框
	        $("#navbar-collapse").collapse('hide');//手机响应式下拉菜单隐藏
	        websocket.send(a);
	        return false;//阻止事件广播
        }
    });
}






$(function(){
	// 先输入名字
	$("#myModal").modal('show');
	$("#newname").focus();
});

function clean(){//清空屏幕
	$("#messageList").html("");
	$("#chat").val("");
	$("#navbar-collapse").collapse('hide');
}

function savename(){// 保存名字
	if(/[.*<|>.*]|.{9}/.test($("#newname").val())||$("#newname").val()==""){
		$("[data-toggle='popover']").popover('show');
		return ;
	}
	$("#username").text($("#newname").val());
	$('#myModal').modal('hide');
	webSocketSession();//进入核心模块
}
// 名字框绑定提示信息事件
$("#newname").focus(function(event) {
	$("[data-toggle='popover']").popover('show'); 
}).blur(function(event) {
	$("[data-toggle='popover']").popover('hide'); 
}).hover(function() {
	$("[data-toggle='popover']").popover('show'); 
}, function() {
	$("[data-toggle='popover']").popover('hide'); 
});
