package com.mcmiddleearth.mcmetours.proxy.core.util;

import com.mcmiddleearth.command.McmeCommandSender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class MessageUtil {

    private final String PREFIX = "[MCME-Tours] ";


    public void sendErrorMessage(CommandSender sender, String message){
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.ERROR).create());
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
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.ERROR).create());
    }

    public void sendNotPossible(McmeCommandSender sender){
        sender.sendMessage(new ComponentBuilder(PREFIX+"This is currently not possible.").color(Style.ERROR).create());
    }

    public void sendInfoMessage(McmeCommandSender sender, String message){
        sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.INFO).create());
    }

    public void sendClickableInfoMessage(CommandSender sender, String message, String clickable){
        TextComponent text = new TextComponent(message);
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,clickable));
        sender.sendMessage(text);
    }

    public String getPrefix(){return PREFIX;}
}
