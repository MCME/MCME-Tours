package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.discord.TourDiscordHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TourRequest {

    public static void informHosts(McmeCommandSender sender, String message){
        boolean success = false;
        if(PluginData.tourRunning()){
            PluginData.getMessageUtil().sendInfoMessage(sender,"There is currently a tour running.");
            return;
        }
        for(ProxiedPlayer player: ProxyServer.getInstance().getPlayers()){
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
