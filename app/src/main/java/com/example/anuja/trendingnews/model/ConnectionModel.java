package com.example.anuja.trendingnews.model;

import com.example.anuja.trendingnews.common.ConnectionStatus;

public class ConnectionModel {

    /**
     * the signal strength
     */
    private ConnectionStatus connectionStatus;

    public ConnectionModel(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }
}
