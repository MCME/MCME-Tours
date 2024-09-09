package com.mcmiddleearth.mcmetours.proxy.velocity.listener;

import com.mcmiddleearth.base.velocity.player.VelocityMcmePlayer;
import com.mcmiddleearth.mcmetours.proxy.core.eventHandler.ChatHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;

public class ChatListener {

    @Subscribe
    public void onChat(PlayerChatEvent event) {
        if(ChatHandler.handle(new VelocityMcmePlayer(event.getPlayer()), event.getMessage())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());
        }
    }
}
