package com.mcmiddleearth.mcmetours.paper.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.mcmiddleearth.mcmetours.paper.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class TourPluginListener implements PluginMessageListener {

    public TourPluginListener(){}

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if(!channel.equals(Channel.MAIN)){
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if(subchannel.equals(Channel.HAT)){
            String playerData = in.readUTF();
            Bukkit.getPlayer(playerData).sendMessage("THIS IS A FUCKING TEST");
        }
    }
}
