package com.mcmiddleearth.mcmetours.listener;

import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mcmiddleearth.mcmetours.util.Style;
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
            PluginData.getMessageUtil().sendInfoMessage(event.getPlayer(),"A tour is currently running. Do "+Style.HIGHLIGHT+"/tour check "+ Style.INFO+" for more information.");
            for(String tourName: PluginData.getTours()){
                Tour tour = PluginData.getTour(tourName);
                if(event.getPlayer().getName().equalsIgnoreCase(tour.getHost().getName()))
                    tour.returnedHost(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
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
            if(event.getPlayer().equals(tour.getHost())){
                tour.selfDestruction();
            }else{
                tour.removePlayer(event.getPlayer());
            }
        }
        TourCommandSender.removeMcmePlayer(event.getPlayer());
    }
}
