package com.mcmiddleearth.mcmetours.listener;

import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * @author Jubo
 */
public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
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
