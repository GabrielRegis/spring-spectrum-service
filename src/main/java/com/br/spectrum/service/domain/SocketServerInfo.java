package com.br.spectrum.service.domain;

import java.util.UUID;

public class SocketServerInfo {
    private String socketServer;

    public SocketServerInfo() {
        this.socketServer = UUID.randomUUID().toString();
    }

    public String getSocketServer() {
        return socketServer;
    }

    public void setSocketServer(String socketServer) {
        this.socketServer = socketServer;
    }
}
