package com.mcmiddleearth.mcmetours.data;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.MCMETours;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageUtil {

    private final String PREFIX = "[MCME-Tours] ";


    public void sendErrorMessage(CommandSender sender, String message){

    }

    public void sendInfoMessage(CommandSender sender, String message){
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.INFO).create());
    }

    public void sendBroadcastMessage(String message){
        for(ProxiedPlayer player: ProxyServer.getInstance().getPlayers()){
            player.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.INFO).create());
        }
    }

    public void sendErrorMessage(McmeCommandSender sender, String message){

    }

    public void sendInfoMessage(McmeCommandSender sender, String message){
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.INFO).create());
    }

}
