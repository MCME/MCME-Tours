package com.mcmiddleearth.mcmetours.discord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.mcmetours.paper.Channel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TourDiscordHandler {

    ProxiedPlayer sender;

    public TourDiscordHandler(ProxiedPlayer host){
        sender = host;
    }

    public void AnnnounceTour(String info){
        String message = ":MCME: ***@Tourist:"+sender.getName()
                +" is hosting a new tour*** :MCME:"
                +"\n To join the tour type in game chat: "
                + "```css\n/tour join " + sender.getName() + "```";
        if(info != null){
            message = message + "__**Tour-Info:**__ "+info;
        }
        handle(sender,DiscordChannel.ALERTS.getDiscordChannel(),message);
    }

    /*
    public void sendTourServerChat(){
        String message = "!\n :ringmcme: **" + sender.getName() + "** is starting a tour! :ringmcme:" +
                "\nTo join the tour type this in game chat: ```css\n/tour join " + sender.getName() + "```";
        handle(sender,DiscordChannel.SERVERCHAT.getDiscordChannel(),message);
    }
     */

    public void endTour(){
        String message = ":MCME: __**Info:**__ "+ sender.getName()+"´s tour has ended.";
        handle(sender,DiscordChannel.ALERTS.getDiscordChannel(),message);
    }

    public void requestTour(){
        String message = sender.getName()+" has requested a tour. There are currently no badge-holders online to help them.";
        handle(sender,DiscordChannel.GUIDECHAT.getDiscordChannel(),message);
    }

    // Will be used when host can be set (not yet decided if implemented) (otherwise will be forgotten)
    public void setSender(ProxiedPlayer sender){
        this.sender = sender;
    }

    // Placeholder for later MCME-Connect implementation
    private boolean handle(ProxiedPlayer sender, String channel, String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.DISCORD);
        out.writeUTF(channel);
        out.writeUTF(message);
        ProxyServer.getInstance().getServerInfo(sender.getServer().getInfo().getName()).sendData(Channel.MAIN,out.toByteArray(),true);
        return true;
    }
}
