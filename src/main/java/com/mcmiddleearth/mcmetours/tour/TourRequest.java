package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TourRequest {

    public TourRequest(McmeCommandSender sender){
        informHosts(sender);
    }

    private void informHosts(McmeCommandSender sender){
        boolean success = false;
        if(PluginData.tourRunning()){
            PluginData.getMessageUtil().sendInfoMessage(sender,"There is currently a tour running.");
            return;
        }
        for(ProxiedPlayer player: ProxyServer.getInstance().getPlayers()){
            if(player.hasPermission(Permission.HOST.getPermissionNode())){
                PluginData.getMessageUtil().sendInfoMessage(player,TourCommandSender.getName((TourCommandSender) sender)+" has requested a tour!");
                success = true;
            }
        }
        if(success){
            PluginData.getMessageUtil().sendInfoMessage(sender, "A guide was informed about your request.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(sender,"There are currently no guides online.");
        }
    }
}
