package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.paper.Channel;

public class TourChat {

    public static void blockChat(McmeProxyPlayer player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channel.TOUR_CHAT);
        out.writeUTF(player.getUniqueId().toString());
        player.getServerInfo().sendPluginMessage(Channel.MAIN,out.toByteArray(),true);
    }
}
