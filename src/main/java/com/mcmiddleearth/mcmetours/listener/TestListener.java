package com.mcmiddleearth.mcmetours.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class TestListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(ServerConnectEvent event){
        if(event.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)){
            ProxiedPlayer player = event.getPlayer();
            player.sendMessage(new ComponentBuilder("This is a test").color(ChatColor.BLUE).create());
        }
    }
}
