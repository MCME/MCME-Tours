package com.mcmiddleearth.mcmetours.proxy.velocity.listener;

import com.mcmiddleearth.base.velocity.player.VelocityMcmePlayer;
import com.mcmiddleearth.mcmetours.proxy.core.eventHandler.ConnectionHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;

public class ConnectionListener {

    @Subscribe
    @SuppressWarnings("UnstableApiUsage")
    public void playerConnect(ServerPostConnectEvent event){
        if(event.getPreviousServer()==null) {
            ConnectionHandler.handlePlayerJoin(new VelocityMcmePlayer(event.getPlayer()));
        } else {
            ConnectionHandler.handlePlayerSwitchServer(new VelocityMcmePlayer(event.getPlayer()));
        }
    }

    @Subscribe
    public void playerLeave(DisconnectEvent event){
        ConnectionHandler.handlePlayerLeave(new VelocityMcmePlayer(event.getPlayer()));
    }

}
