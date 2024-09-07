package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.discord.TourDiscordHandler;

/**
 * @author Jubo
 */
public class TourRequest {

    public static void informHosts(McmeCommandSender sender, String message){
        boolean success = false;
        if(PluginData.tourRunning()){
            PluginData.getMessageUtil().sendClickableInfoMessage((McmeProxyPlayer)((TourCommandSender)sender).getCommandSender(), Style.INFO+PluginData.getMessageUtil().getPrefix()
                    +"There is currently a tour running. "+Style.HIGHLIGHT+"Click here"+Style.INFO+" for more information","/tour check");
            return;
        }
        for(McmeProxyPlayer player: McmeTours.getProxy().getPlayers()){
            if(player.hasPermission(Permission.HOST.getPermissionNode())){
                if(message == null){
                    PluginData.getMessageUtil().sendInfoMessage(player,TourCommandSender.getName((TourCommandSender) sender)+" has requested a tour!");
                }else {
                    PluginData.getMessageUtil().sendInfoMessage(player,TourCommandSender.getName((TourCommandSender) sender)+" has requested a tour! About: "+message);
                }
                success = true;
            }
        }
        if(success){
            PluginData.getMessageUtil().sendInfoMessage(sender, "A guide was informed about your request.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(sender,"There are currently no guides online.");
        }
        new TourDiscordHandler((ProxiedPlayer) ((TourCommandSender)sender).getCommandSender(),null).requestTour(message);
    }
}
