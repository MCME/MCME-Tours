package com.mcmiddleearth.mcmetours.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void TourChat(ChatEvent event){
        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();

    }

}
