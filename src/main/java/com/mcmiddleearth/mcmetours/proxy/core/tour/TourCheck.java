package com.mcmiddleearth.mcmetours.proxy.core.tour;

import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.message.McmeColors;
import com.mcmiddleearth.base.core.message.Message;
import com.mcmiddleearth.base.core.message.MessageClickEvent;
import com.mcmiddleearth.base.core.message.MessageStyle;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TourCheck {

    public static void sendInfos(McmeCommandSender sender){
        if(!PluginData.tourRunning()) {
            sender.sendMessage(McmeTours.infoMessage("There is currently no tour running."));
        }else{
            Message message;
            sender.sendMessage(McmeTours.infoMessage("Running tours (click to join):"));
            for(String tourName : PluginData.getTours()) {
                Tour tour = PluginData.getTour(tourName);
                if (tour!=null && tour.isAnnounced()) {
                    message = McmeTours.getPlugin().createMessage(new MessageStyle(McmeColors.INFO))
                            .add(tour.getHost().getName(), McmeColors.INFO_STRESSED)
                            .add(": Do ").add("/tour join "+tourName,McmeColors.INFO_STRESSED).add(" to join this tour.");
                    if (tour.getInfo() != null) {
                        message.add("About: ").add(tour.getInfo(),McmeColors.INFO_STRESSED);
                    }
                    message.addClick(new MessageClickEvent(MessageClickEvent.Action.RUN_COMMAND,"/tour join "+tourName));
                    sender.sendMessage(message);
                }
            }
        }
    }
}
