package com.mcmiddleearth.mcmetours.paper.functions;

import org.bukkit.entity.Player;

/**
 * @author Jubo
 */
public class TourTeleportPaper {

    public static void teleportPlayer(Player sender, Player destination){
        sender.teleport(destination);
    }
}
