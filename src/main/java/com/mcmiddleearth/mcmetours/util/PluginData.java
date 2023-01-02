package com.mcmiddleearth.mcmetours.util;

import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jubo
 */
public class PluginData {

    private static List<Tour> tours = new ArrayList<>();
    private final static MessageUtil messageUtil = new MessageUtil();

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
            if(tour.getName().equalsIgnoreCase(name))
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
        ProxiedPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour: tours){
            if(tour.getPlayers().contains(player)){
                return true;
            }
        }
        return false;
    }

    public static boolean isCoHost(TourCommandSender sender){
        ProxiedPlayer player = (ProxiedPlayer) sender.getCommandSender();
        for(Tour tour : tours){
            if(tour.getCoHost().contains(player)){
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

    public static List<String> getTours(){
        List<String> host = new ArrayList<>();
        for(Tour tour: tours){
            host.add(tour.getName());
        }
        return host;
    }

    public static List<String> getHostPerms(){
        List<String> hosts = new ArrayList<>();
        for(ProxiedPlayer player: ProxyServer.getInstance().getPlayers()){
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
        ProxiedPlayer Proxyplayer = (ProxiedPlayer) player.getCommandSender();
        return Proxyplayer.hasPermission(perm.getPermissionNode());
    }
}
