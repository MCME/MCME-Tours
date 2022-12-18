package com.mcmiddleearth.mcmetours.listener;

import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * @author Jubo
 */
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerJoin(PostLoginEvent event){
        if(PluginData.tourRunning()){
            PluginData.getMessageUtil().sendInfoMessage(event.getPlayer(),"A tour is currently running.");
        }else{
            PluginData.getMessageUtil().sendInfoMessage(event.getPlayer(),"No tour is currently running.");
        }
    }

    @EventHandler
    public void playerSwitchServer(ServerSwitchEvent event){
        if(PluginData.isCoHost(new TourCommandSender(event.getPlayer()))){
            Tour tour = PluginData.getTour(event.getPlayer());
            tour.setGlow(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerLeave(PlayerDisconnectEvent event){
        if(PluginData.isInTour(new TourCommandSender(event.getPlayer()))){
            Tour tour = PluginData.getTour(new TourCommandSender(event.getPlayer()));
            tour.removePlayer(event.getPlayer());
        }
    }
}
