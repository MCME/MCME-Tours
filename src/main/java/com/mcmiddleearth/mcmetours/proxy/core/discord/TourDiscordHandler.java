package com.mcmiddleearth.mcmetours.proxy.core.discord;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.base.core.server.McmeServerInfo;
import com.mcmiddleearth.mcmetours.paper.Channel;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import net.md_5.bungee.api.CommandSender;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TourDiscordHandler {

    private McmeProxyPlayer sender;
    private final String name;


    public TourDiscordHandler(McmeProxyPlayer host, String name){
        sender = host;
        this.name = name;
    }

    @SuppressWarnings("UnstableApiUsage")
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

    @SuppressWarnings("UnstableApiUsage")
    public void endTour(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.DISCORD);
        out.writeUTF(DiscordMessageType.END);
        out.writeUTF(sender.getName());
        handle(sender,out);
    }

    @SuppressWarnings("UnstableApiUsage")
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

    public void setSender(McmeProxyPlayer sender){
        this.sender = sender;
    }

    private void handle(McmeProxyPlayer sender, ByteArrayDataOutput out){
        McmeServerInfo server = sender.getServerInfo();
        if(server == null) {
            server = McmeTours.getProxy().getAnyConnectedServerInfo();
        }
        if(server!=null) {
            McmeTours.getProxy().getServerInfo(server.getName())
                    .sendPluginMessage(Channel.MAIN, out.toByteArray(), true);
        }
    }
}
