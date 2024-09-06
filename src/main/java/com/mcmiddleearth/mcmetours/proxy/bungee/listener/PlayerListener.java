package com.mcmiddleearth.mcmetours.proxy.bungee.listener;

import com.mcmiddleearth.mcmetours.proxy.core.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.util.Style;
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
            PluginData.getMessageUtil().sendClickableInfoMessage(event.getPlayer(),Style.INFO+PluginData.getMessageUtil().getPrefix()+"A tour is currently running. "
                    +Style.HIGHLIGHT+"Click here"+Style.INFO+" for more information.","/tour check");
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
