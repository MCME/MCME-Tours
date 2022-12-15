package com.mcmiddleearth.mcmetours.discord;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TourDiscordHandler {

    ProxiedPlayer sender;

    public TourDiscordHandler(ProxiedPlayer host){
        sender = host;
    }

    public void AnnnounceTour(){
        String message = "";

        handle(sender,DiscordChannel.ALERTS.getDiscordChannel(),message);
    }

    public void sendTourServerChat(){
        String message = "";

        handle(sender,DiscordChannel.SERVERCHAT.getDiscordChannel(),message);
    }

    public void endTour(){
        String message = "";

        handle(sender,DiscordChannel.ALERTS.getDiscordChannel(),message);
    }

    public void requestTour(ProxiedPlayer requester){
        String message = "";

        handle(sender,DiscordChannel.GUIDECHAT.getDiscordChannel(),message);
    }

    // Will be used when host can be set (not yet decided if implemented) (otherwise will be forgotten)
    public void setSender(ProxiedPlayer sender){
        this.sender = sender;
    }

    // Placeholder for later MCME-Connect implementation
    private void handle(ProxiedPlayer sender, String channel, String message){

    }

}
