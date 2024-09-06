package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.proxy.bungee.MCMETours;
import com.mcmiddleearth.mcmetours.proxy.core.util.ChatRanks;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.util.Style;
import com.mcmiddleearth.mcmetours.proxy.core.discord.TourDiscordHandler;
import com.mcmiddleearth.mcmetours.paper.Channel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jubo
 */
public class Tour {

    private McmeProxyPlayer host;
    private final List<McmeProxyPlayer> players = new ArrayList<>();
    private final List<McmeProxyPlayer> tourChat = new ArrayList<>();
    private final TourDiscordHandler discordHandler;
    private final List<McmeProxyPlayer> coHost = new ArrayList<>();
    private String info = null;
    private boolean glow = false;
    private final String name;
    private boolean announced = false;
    private ScheduledTask cleanup;
    private boolean task = false;
    private final ChatColor MessageColor = ChatColor.WHITE;

    public Tour(McmeProxyPlayer host, String name){
        this.host = host;
        players.add(host);
        tourChat.add(host);
        coHost.add(host);
        this.name = name;
        discordHandler = new TourDiscordHandler(host,name);
        PluginData.getMessageUtil().sendInfoMessage(host,"You started a tour. To put up a description do "+Style.STRESSED+"/tour info <description>"+Style.INFO+".");
        PluginData.getMessageUtil().sendInfoMessage(host,"To announce the tour ingame and in discord do "+Style.STRESSED+"/tour announce <role>"+Style.INFO+".");
    }

    public void selfDestruction(){
        notifyTour("Self-destruction activated! This tour will destroy itself in 60 seconds, if the host doesn´t come back.");
        task = true;
        cleanup = ProxyServer.getInstance().getScheduler().schedule(MCMETours.getInstance(),() -> {
            if(!host.isConnected())
                endTour();
                }, 60, TimeUnit.SECONDS);
    }

    public void addPlayer(McmeProxyPlayer player){
        players.add(player);
        tourChat.add(player);
        teleportHandle(player,host);
        notifyTour("Everybody welcome "+Style.HIGHLIGHT+player.getName()+Style.INFO+" to the tour!");
        PluginData.getMessageUtil().sendInfoMessage(player,"Welcome to the tour. For the best experience, join "+ Style.HIGHLIGHT_STRESSED+host.getName()+Style.INFO+" in Discord!");
    }

    public void removePlayer(McmeProxyPlayer player){
        if(player == host){
            endTour();
            return;
        }
        players.remove(player);
        tourChat.remove(player);
        coHost.remove(player);
        glowHandle(player,false);
        notifyTour(player.getName()+" left the tour.");
        PluginData.getMessageUtil().sendInfoMessage(player, "You left the tour.");
    }

    public void endTour(){
        notifyTour("The tour has ended.");
        players.clear();
        tourChat.clear();
        PluginData.removeTour(this);
        for(McmeProxyPlayer player : coHost){
            glowHandle(player,false);
        }
        if(announced)
            discordHandler.endTour();
        coHost.clear();
    }

    public void TeleportToHost(McmeProxyPlayer player){
        teleportHandle(player,host);
        PluginData.getMessageUtil().sendInfoMessage(player,"You were teleported to "+host.getName()+".");
    }

    public void teleportPlayer(McmeProxyPlayer player,McmeProxyPlayer host){
        if(players.contains(player)){
            teleportHandle(player,host);
            PluginData.getMessageUtil().sendInfoMessage(player,host.getName()+" teleported you to them.");
            PluginData.getMessageUtil().sendInfoMessage(host,"You teleported "+player.getName()+" to yourself.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(host,"This player is not part of the tour.");
        }
    }

    public void teleportAll(McmeProxyPlayer host){
        for(McmeProxyPlayer player: players){
            if(player != host){
                teleportHandle(player,host);
                PluginData.getMessageUtil().sendInfoMessage(player,host.getName()+" teleported you to them.");
            }
        }
        PluginData.getMessageUtil().sendInfoMessage(host, "Players teleported.");
    }

    public void activateTourChat(McmeProxyPlayer player){
        if(tourChat.contains(player)){
            tourChat.remove(player);
            PluginData.getMessageUtil().sendInfoMessage(player,"Tour-Chat deactivated.");
        }else{
            tourChat.add(player);
            PluginData.getMessageUtil().sendInfoMessage(player,"Tour-Chat activated.");
        }
    }

    public void tourChat(McmeProxyPlayer player, String message){
        String ChatMessage;
        for(McmeProxyPlayer receiver : players){
            if(coHost.contains(player)){
                ChatMessage = ChatRanks.HOST.getChatPrefix() + player.getName() + MessageColor + ": "  + message;
            }else if(player.hasPermission(Permission.HOST.getPermissionNode())){
                ChatMessage = ChatRanks.BADGEHOLDER.getChatPrefix() + player.getName() + MessageColor + ": " + message;
            }else{
                ChatMessage = ChatRanks.PARTICIPANT.getChatPrefix() + player.getName() + MessageColor + ": " + message;
            }
            receiver.sendMessage(new ComponentBuilder(ChatMessage).create());
        }
    }

    public void kickPlayer(McmeProxyPlayer player){
        if(player != host){
            removePlayer(player);
            PluginData.getMessageUtil().sendErrorMessage(player,"You were kicked from the tour. Think about it!");
            PluginData.getMessageUtil().sendInfoMessage(host,"You kicked " + player.getName() + " from the tour.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(player,"You can´t kick yourself idiot.");
        }
    }

    public void giveRefreshments(){
        for(ProxiedPlayer player: players){
            refreshmentsHandle(player);
            PluginData.getMessageUtil().sendInfoMessage(player,"You were given refreshments.");
        }
    }

    public void switchGlow(){
        if(!glow){
            glow = true;
            PluginData.getMessageUtil().sendInfoMessage(host,"You switched the glow effect on.");
        }else{
            PluginData.getMessageUtil().sendInfoMessage(host,"You switched the glow effect off.");
            glow = false;
        }
        for(ProxiedPlayer player: coHost){
            glowHandle(player,glow);
        }
    }

    public void setGlow(McmeProxyPlayer player){
        glowHandle(player,glow);
    }

    public void setHost(McmeProxyPlayer host){
        if(!this.host.equals(host)){
            coHost.remove(this.host);
            glowHandle(this.host,false);
            this.host = host;
            discordHandler.setSender(host);
            if(!coHost.contains(host))
                coHost.add(host);
            notifyTour(host.getName()+" is the new host of the tour.");
            glowHandle(host,glow);
        }else
            PluginData.getMessageUtil().sendErrorMessage(this.host,"You are already the host of this tour.");
    }

    public void returnedHost(McmeProxyPlayer returnedHost){
        this.host = returnedHost;
        discordHandler.setSender(returnedHost);
        coHost.add(returnedHost);
        players.add(returnedHost);
        tourChat.add(returnedHost);
        cleanup.cancel();
        task = false;
        notifyTour("The host has returned! Destruction prevented.");
    }

    public void setCoHost(McmeProxyPlayer coHost){
        if(!this.coHost.contains(coHost)){
            glowHandle(coHost,glow);
            this.coHost.add(coHost);
            notifyTour(coHost.getName()+" is now a co host of the tour.");
        }else
            PluginData.getMessageUtil().sendErrorMessage(host,coHost.getName()+" is already a Co-Host.");
    }

    public void removeCoHost(McmeProxyPlayer coHost){
        if(host.equals(coHost)){
            PluginData.getMessageUtil().sendErrorMessage(host,"You can´t remove yourself.");
            return;
        }
        if(this.coHost.remove(coHost)){
            PluginData.getMessageUtil().sendInfoMessage(host,coHost.getName()+" was removed as Co-Host.");
            PluginData.getMessageUtil().sendInfoMessage(coHost,"You were removed as Co-Host.");
        }else
            PluginData.getMessageUtil().sendErrorMessage(host, coHost.getName()+" is not a Co-Host of this tour.");
    }

    public void tourList(McmeProxyPlayer sender){
        Set<String> list_participants;
        Set<String> list_cohosts;
        String host = this.host.getName();
        list_participants = players.stream().map(McmeProxyPlayer::getName).collect(Collectors.toSet());
        list_cohosts = coHost.stream().map(McmeProxyPlayer::getName).collect(Collectors.toSet());
        PluginData.getMessageUtil().sendInfoMessage(sender,ChatColor.WHITE+"Host: "+host);
        PluginData.getMessageUtil().sendInfoMessage(sender,ChatColor.WHITE+"Co-Hosts: "+list_cohosts);
        PluginData.getMessageUtil().sendInfoMessage(sender,ChatColor.WHITE+"Participants: "+list_participants);
    }

    private void notifyTour(String text){
        for(McmeProxyPlayer player: players){
            PluginData.getMessageUtil().sendInfoMessage(player,text);
        }
    }

    public void setInfoText(String info){
        this.info = info;
        PluginData.getMessageUtil().sendInfoMessage(host,"You set the description to: "+info);
    }

    public void sendDAnnouncement(String discordrole){
        if(!announced){
            if(info == null)
                PluginData.getMessageUtil().sendBroadcastMessage(host.getName()+" is hosting a tour. Do "+Style.STRESSED+"/tour join "+ name+Style.INFO+ " to join the tour. Do "
                        +Style.HIGHLIGHT_STRESSED+"/discord"+Style.INFO+" to more information.");
            else
                PluginData.getMessageUtil().sendBroadcastMessage(host.getName()+" is hosting a tour. Do "+Style.STRESSED+"/tour join "+ name+Style.INFO+ " to join the tour. "
                        +Style.HIGHLIGHT+"About: "+info);
            discordHandler.AnnnounceTour(info,discordrole);
            announced = true;
        } else
            PluginData.getMessageUtil().sendErrorMessage(host,"The tour was already announced.");
    }

    private void glowHandle(McmeProxyPlayer sender, boolean bool){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.GLOW);
        out.writeUTF(sender.getName());
        out.writeBoolean(bool);
        ProxyServer.getInstance().getServerInfo(sender.getServer().getInfo().getName()).sendData(Channel.MAIN, out.toByteArray(),true);
    }

    private void refreshmentsHandle(McmeProxyPlayer sender){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.REFRESHMENTS);
        out.writeUTF(sender.getName());
        ProxyServer.getInstance().getServerInfo(sender.getServer().getInfo().getName()).sendData(Channel.MAIN,out.toByteArray(),true);
    }

    private void teleportHandle(McmeProxyPlayer sender, McmeProxyPlayer target){
        if(!sender.hasPermission("mcmeconnect.world."+target.getServer().getInfo().getName())){
            PluginData.getMessageUtil().sendErrorMessage(target,sender.getName()+" doesn´t have the permission to enter this world.");
            PluginData.getMessageUtil().sendErrorMessage(sender, "You don´t have the permission to enter this world.");
            return;
        }
        if(!sender.getServer().getInfo().equals(target.getServer().getInfo())){
            sender.connect(target.getServer().getInfo());
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.TP);
        out.writeUTF(sender.getName());
        out.writeUTF(target.getName());
        ProxyServer.getInstance().getServerInfo(target.getServer().getInfo().getName()).sendData(Channel.MAIN, out.toByteArray(),true);
    }

    public boolean isAnnounced(){return announced;}
    public String getInfo(){return info;}
    public Boolean getTask(){return task;}
    public ScheduledTask getCleanup(){return cleanup;}
    public String getName() {return name;}
    public List<McmeProxyPlayer> getCoHost(){return coHost;}
    public McmeProxyPlayer getHost() {return host;}
    public List<McmeProxyPlayer> getPlayers() { return players;}
    public List<McmeProxyPlayer> getTourChat() { return tourChat;}
}