package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.command.McmeCommandSender;
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
            PluginData.getMessageUtil().sendInfoMessage(sender,"There is at least one tour running:");
            for(String tour : PluginData.getTours()){
                PluginData.getMessageUtil().sendInfoMessage(sender,Style.HIGHLIGHT+PluginData.getTour(tour).getHost().getName()
                        +Style.INFO+": Do "+ Style.STRESSED+"/tour join "+tour+Style.INFO+" to join this tour.");
            }
        }
    }
}
