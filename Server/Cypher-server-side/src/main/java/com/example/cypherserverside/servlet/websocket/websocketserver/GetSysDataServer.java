package com.example.cypherserverside.servlet.websocket.websocketserver;

import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/api/getSysdata")
@Component
public class GetSysDataServer {
    @OnOpen
    public void onOpen(Session session) {}
}
