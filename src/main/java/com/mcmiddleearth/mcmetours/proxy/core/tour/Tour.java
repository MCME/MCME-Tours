package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.base.core.message.*;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.base.core.taskScheduling.Task;
import com.mcmiddleearth.mcmetours.paper.Channel;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.discord.TourDiscordHandler;
import com.mcmiddleearth.mcmetours.proxy.core.util.ChatRanks;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jubo, Eriol_Eandur
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
    private Task cleanup;
    private boolean task = false;
    private final MessageColor messageColor = MessageColor.WHITE;

    public Tour(McmeProxyPlayer host, String name){
        this.host = host;
        players.add(host);
        tourChat.add(host);
        coHost.add(host);
        this.name = name;
        discordHandler = new TourDiscordHandler(host,name);
        host.sendMessage(McmeTours.infoMessage("You started a tour. To put up a description do ")
                .add(McmeTours.getPlugin().createMessage()
                        .add("/tour info <description>",McmeColors.INFO_STRESSED)
                        .addClick(new MessageClickEvent(MessageClickEvent.Action.SUGGEST_COMMAND,"/tour info ")))
                .add(". To announce the tour ingame and in discord do ")
                .add(McmeTours.getPlugin().createMessage()
                        .add("/tour announce <role>",McmeColors.INFO_STRESSED)
                        .addClick(new MessageClickEvent(MessageClickEvent.Action.SUGGEST_COMMAND, "/tour announce ")))
                .add("."));
    }

    public void selfDestruction(){
        notifyTour(McmeTours.infoMessage("Self-destruction activated! This tour will destroy itself in 60 seconds, if the host doesn't come back."));
        task = true;
        cleanup = McmeTours.getPlugin().getTask(() -> {
            if(!host.isConnected())
                endTour();
                });
        cleanup.schedule(60, TimeUnit.SECONDS);
    }

    public void addPlayer(McmeProxyPlayer player){
        players.add(player);
        tourChat.add(player);
        teleportHandle(player,host);
        notifyTour(McmeTours.infoMessage("Everybody welcome ")
                .add(player.getName(),McmeColors.INFO_STRESSED)
                .add(" to the tour!"));
        player.sendMessage(McmeTours.infoMessage("Welcome to the tour. For the best experience, join ")
                .add(host.getName(),McmeColors.INFO_STRESSED)
                .add(" in Discord!"));
    }

    public void removePlayer(McmeProxyPlayer player){
        if(player.equals(host)){
            endTour();
            return;
        }
        players.remove(player);
        tourChat.remove(player);
        coHost.remove(player);
        glowHandle(player,false);
        notifyTour(McmeTours.infoMessage(player.getName()+" left the tour."));
        PluginData.getMessageUtil().sendInfoMessage(player, "You left the tour.");
    }

    public void endTour(){
        notifyTour(McmeTours.infoMessage("The tour has ended."));
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
            if(!player.equals(host)){
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
        Message chatMessage = McmeTours.getPlugin().createMessage(new MessageStyle(messageColor));
        if(coHost.contains(player)){
            chatMessage.add(ChatRanks.HOST.getChatPrefix()+ player.getName(),ChatRanks.HOST.getChatColor());
        }else if(player.hasPermission(Permission.HOST.getPermissionNode())){
            chatMessage.add(ChatRanks.BADGEHOLDER.getChatPrefix() + player.getName(),ChatRanks.BADGEHOLDER.getChatColor());
        }else{
            chatMessage.add(ChatRanks.PARTICIPANT.getChatPrefix() + player.getName(),ChatRanks.PARTICIPANT.getChatColor());
        }
        chatMessage.add(": " + message, messageColor);
        for(McmeProxyPlayer receiver : players){
            receiver.sendMessage(chatMessage);
        }
    }

    public void kickPlayer(McmeProxyPlayer player){
        if(!player.equals(host)){
            removePlayer(player);
            PluginData.getMessageUtil().sendErrorMessage(player,"You were kicked from the tour. Think about it!");
            PluginData.getMessageUtil().sendInfoMessage(host,"You kicked " + player.getName() + " from the tour.");
        }else{
            PluginData.getMessageUtil().sendErrorMessage(player,"You can´t kick yourself idiot.");
        }
    }

    public void giveRefreshments(){
        for(McmeProxyPlayer player: players){
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
        for(McmeProxyPlayer player: coHost){
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
            notifyTour(McmeTours.infoMessage(host.getName()+" is the new host of the tour."));
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
        notifyTour(McmeTours.infoMessage("The host has returned! Destruction prevented."));
    }

    public void setCoHost(McmeProxyPlayer coHost){
        if(!this.coHost.contains(coHost)){
            glowHandle(coHost,glow);
            this.coHost.add(coHost);
            notifyTour(McmeTours.infoMessage(" is now a co host of the tour."));
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
        sender.sendMessage(McmeTours.infoMessage().add("Host: "+host,MessageColor.WHITE));
        sender.sendMessage(McmeTours.infoMessage().add("Co-Hosts: "+list_cohosts,MessageColor.WHITE));
        sender.sendMessage(McmeTours.infoMessage().add("Participants: "+list_participants,MessageColor.WHITE));
    }

    private void notifyTour(Message message){
        for(McmeProxyPlayer player: players){
            player.sendMessage(message);
        }
    }

    public void setInfoText(String info){
        this.info = info;
        PluginData.getMessageUtil().sendInfoMessage(host,"You set the description to: "+info);
    }

    public void sendDAnnouncement(String discordrole){
        if(!announced){
            Message message = McmeTours.getPlugin().createInfoMessage().add(host.getName()+" is hosting a tour. Do ")
                    .add(McmeTours.getPlugin().createMessage()
                            .add("/tour join "+name, McmeColors.INFO_STRESSED)
                            .addClick(new MessageClickEvent(MessageClickEvent.Action.RUN_COMMAND, "/tour join "+name)))
                    .add(" to join the tour. ");
            if(info == null) {
                message.add("Do ")
                       .add(McmeTours.getPlugin().createMessage()
                               .add("/discord",McmeColors.INFO_STRESSED)
                               .addClick(new MessageClickEvent(MessageClickEvent.Action.RUN_COMMAND, "/discord"))
                       .add(" to more information."));
            } else {
                message.add("About: " + info, McmeColors.INFO_STRESSED);
            }
            McmeTours.getProxy().broadcast(message);
            discordHandler.AnnnounceTour(info,discordrole);
            announced = true;
        } else {
            PluginData.getMessageUtil().sendErrorMessage(host, "The tour was already announced.");
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private void glowHandle(McmeProxyPlayer sender, boolean bool){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.GLOW);
        out.writeUTF(sender.getName());
        out.writeBoolean(bool);
        sender.sendDataToBackend(Channel.MAIN, out.toByteArray(),true);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void refreshmentsHandle(McmeProxyPlayer sender){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.REFRESHMENTS);
        out.writeUTF(sender.getName());
        sender.sendDataToBackend(Channel.MAIN,out.toByteArray(),true);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void teleportHandle(McmeProxyPlayer sender, McmeProxyPlayer target){
        if(!sender.hasPermission("mcmeconnect.world."+target.getServerInfo().getName())){
            PluginData.getMessageUtil().sendErrorMessage(target,sender.getName()+" doesn´t have the permission to enter this world.");
            PluginData.getMessageUtil().sendErrorMessage(sender, "You don´t have the permission to enter this world.");
            return;
        }
        if(!sender.getServerInfo().getName().equals(target.getServerInfo().getName())){
            sender.connect(target.getServerInfo(), (result,error)->{});
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.TP);
        out.writeUTF(sender.getName());
        out.writeUTF(target.getName());
        target.sendDataToBackend(Channel.MAIN, out.toByteArray(),true);
    }

    public boolean isAnnounced(){return announced;}
    public String getInfo(){return info;}
    public Boolean getTask(){return task;}
    public Task getCleanup(){return cleanup;}
    public String getName() {return name;}
    public List<McmeProxyPlayer> getCoHost(){return coHost;}
    public McmeProxyPlayer getHost() {return host;}
    public List<McmeProxyPlayer> getPlayers() { return players;}
    public List<McmeProxyPlayer> getTourChat() { return tourChat;}
}