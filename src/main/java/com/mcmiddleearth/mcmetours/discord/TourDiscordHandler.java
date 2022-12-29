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

    private ProxiedPlayer sender;
    private final String name;


    public TourDiscordHandler(ProxiedPlayer host, String name){
        sender = host;
        this.name = name;
    }

    public void AnnnounceTour(String info,String discordrole){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.DISCORD);
        out.writeUTF(DiscordMessageType.ANNOUNCEMENT);
        out.writeUTF(sender.getName());
        out.writeUTF(name);
        out.writeUTF(discordrole);
        if(info == null){
            out.writeBoolean(false);
            out.writeUTF("");
        }
        else{
            out.writeBoolean(true);
            out.writeUTF(info);
        }
        handle(sender,out);
    }

    public void endTour(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.DISCORD);
        out.writeUTF(DiscordMessageType.END);
        out.writeUTF(sender.getName());
        handle(sender,out);
    }

    public void requestTour(String messageRequest){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.DISCORD);
        out.writeUTF(DiscordMessageType.REQUEST);
        if(messageRequest == null){
            out.writeBoolean(false);
            out.writeUTF("");
        }
        else{
            out.writeBoolean(true);
            out.writeUTF(messageRequest);
        }
        out.writeUTF(sender.getName());
        handle(sender,out);
    }

    // Will be used when host can be set (not yet decided if implemented) (otherwise will be forgotten)
    public void setSender(ProxiedPlayer sender){
        this.sender = sender;
    }

    // Placeholder for later MCME-Connect implementation
    private void handle(ProxiedPlayer sender, ByteArrayDataOutput out){
        ProxyServer.getInstance().getServerInfo(sender.getServer().getInfo().getName()).sendData(Channel.MAIN,out.toByteArray(),true);
    }
}
