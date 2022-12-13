package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.connect.bungee.Handler.TpHandler;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.data.Style;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tour {

    private ProxiedPlayer host;
    private List<ProxiedPlayer> players = new ArrayList<>();
    private List<ProxiedPlayer> tourChat = new ArrayList<>();

    public Tour(ProxiedPlayer host){
        this.host = host;
        players.add(host);
        tourChat.add(host);
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

        // First test if this creates a error
        if(player.isConnected()){

        }
        notifyTour(player.getName()+" left the tour.");
        PluginData.getMessageUtil().sendInfoMessage(player, "You left the tour.");
    }

    public void endTour(){

    }

    public void TeleportToHost(ProxiedPlayer player){
        TpHandler.handle(player.getName(),host.getServer().getInfo().getName(),host.getName());
        PluginData.getMessageUtil().sendInfoMessage(player,"You were teleport to "+host.getName()+".");
    }

    public void teleportPlayer(ProxiedPlayer player){
        if(players.contains(player)){
            TpHandler.handle(player.getName(),host.getServer().getInfo().getName(),host.getName());
            PluginData.getMessageUtil().sendInfoMessage(player,host.getName()+" teleported you to them.");
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

    public void kickPlayer(ProxiedPlayer player){

    }

    public void giveRefreshments(){
        //might not be possible
    }

    public void tourHat(){
        //might not be possible
    }

    public String tourList(){
        String list = "";

        return list;
    }

    private void notifyTour(String text){

    }

    public ProxiedPlayer getHost() {return host;}
    public List<ProxiedPlayer> getPlayers() { return players;}
    public List<ProxiedPlayer> getTourChat() { return tourChat;}


    /*
     protected AbstractGame getGame(Player player) {
        AbstractGame game = PluginData.getGame(player);
        if(game==null) {
            sendNotInGameErrorMessage(player);
        }
        return game;
    }
     */
}
