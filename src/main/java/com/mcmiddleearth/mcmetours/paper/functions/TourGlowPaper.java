package com.mcmiddleearth.mcmetours.paper.functions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TourGlowPaper {

    public static void setGlow(String playerData, String boolData){
        Player player = Bukkit.getPlayer(playerData);
        //player.sendMessage("Test Glow");
        player.setGlowing(Boolean.parseBoolean(boolData));
    }
}
