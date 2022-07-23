package com.microservice.alimentos.alimentos.Sockets;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.microservice.alimentos.alimentos.Sockets.service.SocketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor

public class WebSocketsEvents {
    private final SocketService socketService;


  @EventListener
  public void onDisconnectEvent(SessionDisconnectEvent event) {

    log.debug("Client with session id {} disconnected", event.getSessionId());

    String sessionId = event.getSessionId();

    String name = socketService.getNameBySession(sessionId);

    log.debug("Client with name {} has been disconnected ", name);

    socketService.removeSession(sessionId);

  }
}
