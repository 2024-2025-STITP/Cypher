package com.example.cypherserverside.servlet;

import com.example.cypherserverside.service.collect.SysDataServer;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;


//doto：Message Quene
@Slf4j
@ServerEndpoint(value = "/ws/url")
@Component
public class WebSocketServer implements ApplicationContextAware {

    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static ApplicationContext _applicationContext;

    private static SysDataServer sysDataServer;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;
        startScheduledTask();
    }

    /**
     * 开始连接
     * @param session 客户端传来的session
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
        session.setMaxIdleTimeout(30000);
        System.out.println("连接：" + session.getId());
    }

    /**
     * 收到客户端消息
     * @param message 来自前端的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("onMessage: " + message);
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose(Session session) {
        for (String key : sessions.keySet()) {
            if (key.equals(session.getId())) {
                sessions.remove(key);
                System.out.println("关闭" + session.getId());
            }
        }
    }

    /**
     * 连接失败出现异常
     * @param t 异常
     */
    @OnError
    public void onError(Throwable t) {
        log.error(t.getMessage(), t);
    }

    /**
     * 定时任务启动
     */
    private static void startScheduledTask(){
        scheduler.scheduleAtFixedRate(()->{
            sysDataServer = _applicationContext.getBean(SysDataServer.class);
            String systemMessage = sysDataServer.toJSONString(sysDataServer.collectData());
            broadcast(systemMessage);
        }, 0, 2, TimeUnit.SECONDS);
    }

    private static void broadcast(String message){
        for (Session session : sessions.values()) {
            session.getAsyncRemote().sendText(message);
            System.out.println("已发送：" + session.getId());
        }
    }
}
