package com.mcmiddleearth.mcmetours.proxy.core.eventHandler;

import com.mcmiddleearth.base.bungee.player.BungeeMcmePlayer;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

/**
 * @author Jubo, Eriol_Eandur
 */
public class ChatHandler {
    public static boolean handle(BungeeMcmePlayer sender, String message) {
        if(!message.startsWith("/")){
            Tour tour = PluginData.getTour(sender);
            if(tour != null ) {
                if(tour.getTourChat().contains(sender)) {
                    tour.tourChat(sender,message);
                    return true;
                }
            }
        }
        return false;
    }
}
