package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.discord.TourDiscordHandler;
import com.mcmiddleearth.mcmetours.proxy.core.util.MessageUtil;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TourRequest {

    public static void informHosts(McmeProxyPlayer sender, String message){
        boolean success = false;
        if(PluginData.tourRunning()){
            sender.sendMessage(MessageUtil.runningTourInfo());
            return;
        }
        for(McmeProxyPlayer player: McmeTours.getProxy().getPlayers()){
            if(player.hasPermission(Permission.HOST.getPermissionNode())){
                sender.sendMessage(MessageUtil.tourRequest(player, message));
                success = true;
            }
        }
        if(success){
            sender.sendMessage(McmeTours.infoMessage("A guide was informed about your request."));
        }else{
            sender.sendMessage(McmeTours.infoMessage("There are currently no guides online."));
        }
        new TourDiscordHandler(sender,null).requestTour(message);
    }
}
