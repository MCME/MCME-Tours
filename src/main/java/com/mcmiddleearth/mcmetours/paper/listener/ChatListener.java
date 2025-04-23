package com.mcmiddleearth.mcmetours.paper.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class ChatListener implements Listener {

    public static Set<UUID> tourChatBlock = new HashSet<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if(tourChatBlock.contains(event.getPlayer().getUniqueId())) {
            tourChatBlock.remove(event.getPlayer().getUniqueId());
            event.setCancelled(true);
Logger.getGlobal().info("Chat blocked!!!");
        }
    }

    public static void addTourChatBlock(UUID uuid) {
        tourChatBlock.add(uuid);
    }
}
