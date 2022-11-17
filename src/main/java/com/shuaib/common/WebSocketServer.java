package com.shuaib.common;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@ServerEndpoint(value = "/websocket/{openId}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。把它设计成线程安全的。
    private static final AtomicInteger onlineCount = new AtomicInteger();

    //用来存放每个客户端对应的WebSocketServer对象
    private static final ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 建立连接成功调用
     *
     * @param session 建立的会话
     * @param openId  连接者编号
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "openId") String openId) {
        sessionPools.put(openId, session);
        addOnlineCount();
        System.out.println(openId + "加入群聊," + "当前人数为:" + onlineCount);
        try {
            session.getBasicRemote().sendText("欢迎[" + openId + "]加入连接！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开连接时触发
     *
     * @param openId 断开连接的编号
     */
    @OnClose
    public void onClose(@PathParam(value = "openId") String openId) {
        sessionPools.remove(openId);
        subOnlineCount();
        System.out.println(openId + "断开连接," + "当前人数为:" + onlineCount);
    }

    /**
     * 收到客户端信息
     *
     * @param messageObj 消息对象
     * @param openId     发送者编号
     */
    @OnMessage
    public void onMessage(String messageObj, @PathParam("openId") String openId) {
        System.out.println("客户端已收到消息:" + messageObj);
        JSONObject obj = JSONUtil.parseObj(messageObj);
        String message = obj.getStr("message");
        String receiver = obj.getStr("receiver");
        Session receiverSession = sessionPools.get(receiver);
        if (receiverSession != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("receiver", receiver);
            jsonObject.set("sender", openId);
            jsonObject.set("message", message);
            this.sendMessage(jsonObject.toString(), receiverSession);
        } else {
            System.out.println("发送失败,未找到[" + receiver + "]用户");
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, @NotNull Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 给指定用户发送信息
     *
     * @param message         消息对象
     * @param receiverSession 收信人的会话
     */
    public void sendMessage(String message, @NotNull Session receiverSession) {
        System.out.println("[" + JSONUtil.parseObj(message).getStr("sender") + "]给["
                + JSONUtil.parseObj(message).getStr("receiver") + "]发送数据:"
                + JSONUtil.parseObj(message).getStr("message"));
        try {
            receiverSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 人数加1
     */
    public static void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    /**
     * 人数减1
     */
    public static void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}
