package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.mcmetours.paper.Channel;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TourHat {

    @SuppressWarnings("UnstableApiUsage")
    public static void handle(String sender, String server){
        PluginData.getMessageUtil().sendInfoMessage(McmeTours.getProxy().getPlayer(sender),"Tour-Hat set.");
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.HAT);
        out.writeUTF(sender);
        McmeTours.getProxy().getServerInfo(server).sendPluginMessage(Channel.MAIN,out.toByteArray(),true);
    }
}
