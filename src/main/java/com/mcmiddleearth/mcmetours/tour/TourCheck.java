package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.util.Style;

/**
 * @author Jubo
 */
public class TourCheck {

    public static void sendInfos(McmeCommandSender sender){
        if(!PluginData.tourRunning()) {
            PluginData.getMessageUtil().sendInfoMessage(sender, "There is currently no tour running.");
        }else{
            String message;
            PluginData.getMessageUtil().sendInfoMessage(sender,"Running tours (click to join):");
            for(String tour : PluginData.getTours()){
                message = Style.HIGHLIGHT+PluginData.getTour(tour).getHost().getName()
                        +Style.INFO+": Do "+ Style.STRESSED+"/tour join "+tour+Style.INFO+" to join this tour.";
                if(PluginData.getTour(tour).getInfo() != null){
                    message = message + "About: "+ PluginData.getTour(tour).getInfo();
                }
                PluginData.getMessageUtil().sendClickableInfoMessage(((TourCommandSender)sender).getCommandSender(),message,"/tour join "+tour);
            }
        }
    }
}
