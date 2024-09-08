package com.mcmiddleearth.mcmetours.proxy.bungee.listener;

import com.mcmiddleearth.base.bungee.player.BungeeMcmePlayer;
import com.mcmiddleearth.mcmetours.proxy.core.eventHandler.ChatHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * @author Jubo, Eriol_Eandur
 */
public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void TourChat(ChatEvent event){
        if(ChatHandler.handle(new BungeeMcmePlayer((ProxiedPlayer) event.getSender()), event.getMessage())) {
            event.setCancelled(true);
        }
    }
}
