package com.mcmiddleearth.mcmetours.data;

import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jubo
 */
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

    public static Tour getTour(ProxiedPlayer player){
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player))
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

    public static boolean isInTour(TourCommandSender sender){
        ProxiedPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player)){
                return true;
            }
        }
        //PluginData.getMessageUtil().sendErrorMessage(sender,"You are currently not in a tour.");
        return false;
    }

    public static boolean isInTour(ProxiedPlayer player){
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
        //PluginData.getMessageUtil().sendErrorMessage(sender,"You are not the host of this tour.");
        return false;
    }

    public static void clearTours(){
        tours.clear();
    }

    public static MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public static boolean hasPermission(TourCommandSender player, Permission perm) {
        ProxiedPlayer Proxyplayer = (ProxiedPlayer) player.getCommandSender();
        //Proxyplayer.sendMessage(new ComponentBuilder("Test5").color(ChatColor.BLUE).create());
        return Proxyplayer.hasPermission(perm.getPermissionNode());
    }
}
