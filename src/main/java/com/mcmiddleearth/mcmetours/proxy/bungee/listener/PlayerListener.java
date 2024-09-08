package com.mcmiddleearth.mcmetours.proxy.bungee.listener;

import com.mcmiddleearth.base.bungee.player.BungeeMcmePlayer;
import com.mcmiddleearth.mcmetours.proxy.core.eventHandler.ConnectionHandler;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * @author Jubo, Eriol_Eandur
 */
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerJoin(PostLoginEvent event){
        ConnectionHandler.handlePlayerJoin(new BungeeMcmePlayer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerSwitchServer(ServerSwitchEvent event){
        ConnectionHandler.handlePlayerSwitchServer(new BungeeMcmePlayer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerLeave(PlayerDisconnectEvent event){
        ConnectionHandler.handlePlayerLeave(new BungeeMcmePlayer(event.getPlayer()));
    }
}
