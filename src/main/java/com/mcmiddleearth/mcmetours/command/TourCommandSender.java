package com.mcmiddleearth.mcmetours.command;

import com.mcmiddleearth.command.McmeCommandSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Jubo
 */
public class TourCommandSender implements McmeCommandSender {

    private final CommandSender sender;

    public static HashMap<UUID, TourCommandSender> players = new HashMap<>();

    public TourCommandSender(CommandSender sender){
        this.sender = sender;
    }

    public CommandSender getCommandSender() {
        return sender;
    }

    @Override
    public void sendMessage(BaseComponent[] baseComponents) {
        sender.sendMessage(baseComponents);
    }

    public static String getName(TourCommandSender sender){
        return sender.getCommandSender().getName();
    }

    public static TourCommandSender getOrCreateMcmePlayer(ProxiedPlayer player){
        TourCommandSender result = players.get(player.getUniqueId());
        if(result == null){
            result = new TourCommandSender(player);
            players.put(player.getUniqueId(),result);
        }
        return result;
    }

    public static void removeMcmePlayer(ProxiedPlayer player){
        players.remove(player.getUniqueId());
    }
}
