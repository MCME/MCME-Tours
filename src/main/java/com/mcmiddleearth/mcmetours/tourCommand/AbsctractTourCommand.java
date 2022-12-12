package com.mcmiddleearth.mcmetours.tourCommand;

import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class AbsctractTourCommand {

    public AbsctractTourCommand(){
    }

    protected Tour getTour(ProxiedPlayer player){
        Tour tour = PluginData.getTour(player);
        if(tour == null){

        }
        return tour;
    }
}
