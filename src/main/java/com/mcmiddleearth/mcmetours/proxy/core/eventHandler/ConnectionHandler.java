package com.mcmiddleearth.mcmetours.proxy.core.eventHandler;

import com.mcmiddleearth.base.bungee.player.BungeeMcmePlayer;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.util.MessageUtil;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

/**
 * @author Jubo, Eriol_Eandur
 */
public class ConnectionHandler {

    public static void handlePlayerJoin(McmeProxyPlayer player) {
        if(PluginData.tourRunning()){
            player.sendMessage(MessageUtil.runningTourInfo());
            for(String tourName: PluginData.getTours()){
                Tour tour = PluginData.getTour(tourName);
                if(tour!=null && player.getName().equalsIgnoreCase(tour.getHost().getName()))
                    tour.returnedHost(player);
            }
        }
    }

    public static void handlePlayerSwitchServer(McmeProxyPlayer player) {
        if(PluginData.isCoHost(player)){
            Tour tour = PluginData.getTour(player);
            if(tour!=null) tour.setGlow(player);
        }
    }

    public static void handlePlayerLeave(McmeProxyPlayer player) {
        if(PluginData.isInTour(player)){
            Tour tour = PluginData.getTour(player);
            if(tour!=null) {
                if(player.equals(tour.getHost())){
                    tour.selfDestruction();
                }else {
                    tour.removePlayer(player);
                }
            }
        }
    }
}
