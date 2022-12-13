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

    public static Tour getTour(TourCommandSender sender){
        ProxiedPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player))
                return tour;
        }
        return null;
    }

    public static Tour getTour(String name){
        for(Tour tour: tours){
            if(tour.getHost().getName().equalsIgnoreCase(name))
                return tour;
        }
        return null;
    }

    public static boolean tourRunning(){
        if(tours.size() >= 1){
            return true;
        }
        return false;
    }

    public static void addTour(Tour tour){
        tours.add(tour);
    }

    public static void removeTour(Tour tour){
        tours.remove(tour);
    }

    public static boolean isInGame(TourCommandSender sender){
        ProxiedPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player)){
                return true;
            }
        }
        return false;
    }

    public static boolean isHost(TourCommandSender sender){
        ProxiedPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getHost() == player){
                return true;
            }
        }
        return false;
    }


    public static MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public static boolean hasPermission(TourCommandSender player, Permission perm) {
        ProxiedPlayer Proxyplayer = (ProxiedPlayer) player.getCommandSender();
        //Proxyplayer.sendMessage(new ComponentBuilder("Test5").color(ChatColor.BLUE).create());
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
