package com.mcmiddleearth.mcmetours.command;

import com.mcmiddleearth.command.McmeCommandSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class TourCommandSender implements McmeCommandSender {

    CommandSender sender;

    public static HashMap<UUID, TourCommandSender> players = new HashMap<>();

    public TourCommandSender(CommandSender sender){
        this.sender = sender;
    }

    public CommandSender getCommandSender() {
        return sender;
    }

    @Override
    public void sendMessage(BaseComponent[] baseComponents) {
        sender.sendMessage(new ComponentBuilder("[Tours] ").color(ChatColor.AQUA).append(baseComponents[0]).create());
    }

    public static TourCommandSender getOrCreateMcmePlayer(ProxiedPlayer player){
        TourCommandSender result = players.get(player.getUniqueId());
        if(result == null){
            result = new TourCommandSender(player);
            players.put(player.getUniqueId(),result);
        }
        return result;
    }
}
