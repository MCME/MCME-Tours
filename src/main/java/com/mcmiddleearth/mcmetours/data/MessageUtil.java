package com.mcmiddleearth.mcmetours.data;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.MCMETours;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageUtil {

    public static final ChatColor INFO = ChatColor.AQUA;
    public static final ChatColor ERROR = ChatColor.RED;
    public static final ChatColor ERROR_STRESSED = ChatColor.DARK_RED;
    public static final ChatColor STRESSED = ChatColor.GREEN;
    public static final ChatColor HIGHLIGHT = ChatColor.GOLD;
    public static final ChatColor HIGHLIGHT_STRESSED = ChatColor.YELLOW;

    private final String PREFIX = "[MCME-Tours] ";


    public void sendErrorMessage(CommandSender sender, String message){

    }

    public void sendInfoMessage(CommandSender sender, String message){
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(INFO).create());
    }

    public void sendBroadcastMessage(String message){
        for(ProxiedPlayer player: ProxyServer.getInstance().getPlayers()){

        }
    }

    public void sendErrorMessage(McmeCommandSender sender, String message){

    }

    public void sendInfoMessage(McmeCommandSender sender, String message){
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(INFO).create());
    }


    public ChatColor getInfo(){return INFO;}

    public ChatColor getError(){return ERROR;}

}
