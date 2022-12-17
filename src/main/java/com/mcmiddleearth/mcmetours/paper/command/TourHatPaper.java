package com.mcmiddleearth.mcmetours.paper.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TourHatPaper {

    public TourHatPaper(){}

    public static void TourHat(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
    }
}

