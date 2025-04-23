package com.mcmiddleearth.mcmetours.proxy.velocity.listener;

import com.mcmiddleearth.base.velocity.player.VelocityMcmePlayer;
import com.mcmiddleearth.base.velocity.server.VelocityMcmeProxy;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.eventHandler.ChatHandler;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.tour.TourChat;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;

public class ChatListener {

    @Subscribe
    public void onChat(PlayerChatEvent event) {
        if(ChatHandler.handle(new VelocityMcmePlayer(event.getPlayer()), event.getMessage())) {
McmeTours.getLogger().warn("Blocking tour chat!");
            TourChat.blockChat(VelocityMcmeProxy.getPlayer(event.getPlayer()));
            //event.setResult(PlayerChatEvent.ChatResult.message("test"));
        }
    }
}
