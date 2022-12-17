package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.connect.bungee.Handler.TpHandler;
import com.mcmiddleearth.mcmetours.data.ChatRanks;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.data.Style;
import com.mcmiddleearth.mcmetours.discord.TourDiscordHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jubo
 */
public class Tour {

    private ProxiedPlayer host;
    private List<ProxiedPlayer> players = new ArrayList<>();
    private List<ProxiedPlayer> tourChat = new ArrayList<>();
    private final TourDiscordHandler discordHandler;
    private List<ProxiedPlayer> coHost = new ArrayList<>();

    public Tour(ProxiedPlayer host){
        this.host = host;
        players.add(host);
        tourChat.add(host);
        setGlow(host);
        coHost.add(host);
        discordHandler = new TourDiscordHandler(host);
        PluginData.getMessageUtil().sendBroadcastMessage(host.getName()+" is hosting a tour. Do "+Style.STRESSED+"/tour join "+ host.getName()+Style.INFO+ " to join the tour");
    }

    public void addPlayer(ProxiedPlayer player){
        players.add(player);
        tourChat.add(player);
        TpHandler.handle(player.getName(),host.getServer().getInfo().getName(),host.getName());
        notifyTour("Everyboy welcome "+Style.HIGHLIGHT+player.getName()+Style.INFO+" to the tour!");
        PluginData.getMessageUtil().sendInfoMessage(player,"Welcome to the tour. For the best experience, join "+ Style.HIGHLIGHT_STRESSED+host.getName()+Style.INFO+" in Discord!");
    }

    public void removePlayer(ProxiedPlayer player){
        if(player == host){
            endTour();
            return;
        }
        players.remove(player);
        tourChat.remove(player);
        notifyTour(player.getName()+" left the tour.");
        PluginData.getMessageUtil().sendInfoMessage(player, "You left the tour.");
    }

    public void endTour(){
        notifyTour("The tour has ended.");
        players.clear();
        tourChat.clear();
        PluginData.removeTour(this);
    }

    public void TeleportToHost(ProxiedPlayer player){
        TpHandler.handle(player.getName(),host.getServer().getInfo().getName(),host.getName());
        PluginData.getMessageUtil().sendInfoMessage(player,"You were teleport to "+host.getName()+".");
    }

    public void teleportPlayer(ProxiedPlayer player){
        if(players.contains(player)){
            TpHandler.handle(player.getName(),host.getServer().getInfo().getName(),host.getName());
            PluginData.getMessageUtil().sendInfoMessage(player,host.getName()+" teleported you to them.");
            PluginData.getMessageUtil().sendInfoMessage(host,"You teleported "+player.getName()+" to yourself.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(host,"This player is not part of the tour.");
        }
    }

    public void teleportAll(){
        for(ProxiedPlayer player: players){
            if(player != host){
                TpHandler.handle(player.getName(),host.getServer().getInfo().getName(),host.getName());
                PluginData.getMessageUtil().sendInfoMessage(player,host.getName()+" teleported you to them.");
            }
        }
        PluginData.getMessageUtil().sendInfoMessage(host, "Players teleported.");
    }

    public void activateTourChat(ProxiedPlayer player){
        if(tourChat.contains(player)){
            tourChat.remove(player);
            PluginData.getMessageUtil().sendInfoMessage(player,"Tour-Chat deactivated.");
        }else{
            tourChat.add(player);
            PluginData.getMessageUtil().sendInfoMessage(player,"Tour-Chat activated.");
        }
    }

    public void tourChat(ProxiedPlayer player, String message){
        String ChatMessage;
        for(ProxiedPlayer receiver : tourChat){
            if(coHost.contains(player)){
                ChatMessage = ChatRanks.HOST.getChatPrefix() + player.getName() + ChatColor.WHITE + ": "  + message;
            }else if(player.hasPermission(Permission.HOST.getPermissionNode())){
                ChatMessage = ChatRanks.BADGEHOLDER.getChatPrefix() + player.getName() + ChatColor.WHITE + ": " + message;
            }else{
                ChatMessage = ChatRanks.PARTICIPANT.getChatPrefix() + player.getName() + ChatColor.WHITE + ": " + message;
            }
            receiver.sendMessage(new ComponentBuilder(ChatMessage).create());
        }
    }

    public void kickPlayer(ProxiedPlayer player){
        if(player != host){
            removePlayer(player);
            PluginData.getMessageUtil().sendErrorMessage(player,"You were kicked from the tour. Think about it!");
            PluginData.getMessageUtil().sendInfoMessage(host,"You kicked " + player.getName() + " from the tour.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(player,"You can´t kick yourself idiot.");
        }
    }

    public void giveRefreshments(){
        //Bukkit Part missing
    }

    private void setGlow(ProxiedPlayer player){
        //Bukkit Part missing
    }

    public void setHost(ProxiedPlayer host){
        this.host = host;
    }

    public void setCoHost(ProxiedPlayer coHost){
        setGlow(coHost);
        this.coHost.add(coHost);
    }

    public void tourList(){
        Set<String> list;
        list = players.stream().map(ProxiedPlayer::getName).collect(Collectors.toSet());
        PluginData.getMessageUtil().sendInfoMessage(host,ChatColor.WHITE+list.toString());
    }

    private void notifyTour(String text){
        for(ProxiedPlayer player: players){
            PluginData.getMessageUtil().sendInfoMessage(player,text);
        }
    }

    public List<ProxiedPlayer> getCoHost(){return coHost;}
    public ProxiedPlayer getHost() {return host;}
    public List<ProxiedPlayer> getPlayers() { return players;}
    public List<ProxiedPlayer> getTourChat() { return tourChat;}
}
