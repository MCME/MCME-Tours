package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.paper.command.TourHatPaper;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TourHat {

    public TourHat(McmeCommandSender sender){
        setHat((ProxiedPlayer) ((TourCommandSender)sender).getCommandSender());
    }

    private void setHat(ProxiedPlayer player){
        TourHatPaper.TourHat(player.getName());
        PluginData.getMessageUtil().sendInfoMessage(player,"Your hat was set.");
    }

}
