package com.mcmiddleearth.mcmetours.paper.functions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Jubo
 */
public class TourGlowPaper {

    public static void setGlow(String playerData, Boolean bool){
        Player player = Bukkit.getPlayer(playerData);
        player.setGlowing(bool);
    }
}
