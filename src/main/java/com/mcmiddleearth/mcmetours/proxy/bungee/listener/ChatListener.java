package com.mcmiddleearth.mcmetours.proxy.bungee.listener;

import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * @author Jubo
 */
public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void TourChat(ChatEvent event){
        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
        String message = event.getMessage();
        if(!message.startsWith("/")){
            Tour tour = PluginData.getTour(sender);
            if(tour != null ) {
                if(tour.getTourChat().contains(sender)) {
                    tour.tourChat(sender,message);
                    event.setCancelled(true);
                }
            }
        }
    }
}
