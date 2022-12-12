package com.mcmiddleearth.mcmetours.tourCommand;

import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RequestTourCommand extends AbsctractTourCommand {

    public RequestTourCommand(){
        super();
    }

    /*
    public void sendRequest(ProxiedPlayer sender){
        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
            if(PluginData.hasPermission(sender,Permission.HOST)){
                player.sendMessage(new ComponentBuilder("[MCME-Tour] "+sender.getName()+" has requested a tour.").color(ChatColor.AQUA).create());
            }
        }
    }

     */
}
