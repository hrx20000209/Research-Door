package com.example.researchgate.Utils.WebSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component

@ServerEndpoint(value = "/Jqcase")

public class JqWebSocket {

    private Session session = null;
    private Integer linkCount=0;
    private static CopyOnWriteArraySet<JqWebSocket> webSocketSet= new CopyOnWriteArraySet<>();

    /**
     * 新的客户端连接调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("-------------有新的客户端连接----------");
        linkCount++;
        this.session = session;
        webSocketSet.add(this);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("发生变化"+message);
        try{
            sendMessage("发生变化"+message);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    /**
     * 发送信息调用的方法
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String message) throws IOException {
        for (JqWebSocket item : webSocketSet) {
            item.session.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(){
        //thread1.stopMe();
        linkCount--;
        webSocketSet.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        System.out.println(error.toString());
    }
}
