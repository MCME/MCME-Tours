package com.mcmiddleearth.mcmetours.proxy.core.util;

import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jubo
 */
public class PluginData {

    private static final List<Tour> tours = new ArrayList<>();
    private final static MessageUtil messageUtil = new MessageUtil();

    public static Tour getTour(TourCommandSender sender){
        McmeProxyPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player))
                return tour;
        }
        return null;
    }

    public static Tour getTour(String name){
        for(Tour tour: tours){
            if(tour.getName().equalsIgnoreCase(name))
                return tour;
        }
        return null;
    }

    public static Tour getTour(McmeProxyPlayer player){
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player))
                return tour;
        }
        return null;
    }

    public static boolean tourRunning(){
        for(Tour tour : tours){
            if(tour.isAnnounced())
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
        McmeProxyPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player)){
                return true;
            }
        }
        return false;
    }

    public static boolean isCoHost(TourCommandSender sender){
        McmeProxyPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour : tours){
            if(tour.getCoHost().contains(player)){
                return true;
            }
        }
        return false;
    }

    public static boolean isHost(TourCommandSender sender){
        McmeProxyPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getHost() == player){
                return true;
            }
        }
        return false;
    }

    public static List<String> getTours(){
        List<String> host = new ArrayList<>();
        for(Tour tour: tours){
            host.add(tour.getName());
        }
        return host;
    }

    public static List<String> getHostPerms(){
        List<String> hosts = new ArrayList<>();
        for(McmeProxyPlayer player: McmeTours.getProxy().getPlayers()){
            if(player.hasPermission(Permission.HOST.getPermissionNode())){
                hosts.add(player.getName());
            }
        }
        return hosts;
    }

    public static void clearTours(){
        tours.clear();
    }

    public static MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public static boolean hasPermission(TourCommandSender player, Permission perm) {
        McmeProxyPlayer Proxyplayer = (ProxiedPlayer) player.getCommandSender();
        return Proxyplayer.hasPermission(perm.getPermissionNode());
    }
}
