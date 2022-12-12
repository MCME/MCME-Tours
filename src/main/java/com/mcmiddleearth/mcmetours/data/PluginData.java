package com.mcmiddleearth.mcmetours.data;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class PluginData {

    private static List<Tour> tours = new ArrayList<>();

    private final static MessageUtil messageUtil = new MessageUtil();

    PluginData(){

    }

    public static Tour getTour(ProxiedPlayer player){
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player))
                return tour;
        }
        return null;
    }


    public static MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public static boolean hasPermission(TourCommandSender player, Permission perm) {
        ProxiedPlayer Proxyplayer = (ProxiedPlayer) player.getCommandSender();
        Proxyplayer.sendMessage(new ComponentBuilder("Test5").color(ChatColor.BLUE).create());
        return Proxyplayer.hasPermission(perm.getPermissionNode());
    }
    /*
    public static AbstractGame getGame(Player player) {
        for(AbstractGame game : games) {
            if(PlayerUtil.isSame(game.getManager(),player)) {
                return game;
            }
            if(game.getPlayers().contains(player.getUniqueId())) {
                return game;
            }
        }
        return null;
    }
     */
}
