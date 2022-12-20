package com.mcmiddleearth.mcmetours.tour;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.mcmetours.paper.Channel;
import com.mcmiddleearth.mcmetours.util.PluginData;
import net.md_5.bungee.api.ProxyServer;

/**
 * @author Jubo
 */
public class TourHat {

    public static void handle(String sender, String server){
        PluginData.getMessageUtil().sendInfoMessage(ProxyServer.getInstance().getPlayer(sender),"Tour-Hat set.");
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.HAT);
        out.writeUTF(sender);
        ProxyServer.getInstance().getServerInfo(server).sendData(Channel.MAIN,out.toByteArray(),true);
    }
}
