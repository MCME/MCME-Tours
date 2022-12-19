package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;

/**
 * @author Jubo
 */
public class TtpaCommandHandler extends AbstractCommandHandler{

    public TtpaCommandHandler(String name){
        super(name);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .withHelpText("TestText1")
                .withTooltip("TestText2")
                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isCoHost((TourCommandSender) sender)))
                .executes(context -> teleportAll(context.getSource()));
        return helpfulLiteralBuilder;
    }

    private int teleportAll(McmeCommandSender sender){
        Tour tour = PluginData.getTour((TourCommandSender) sender);
        if(tour != null){
            tour.teleportAll();
        }
        return 0;
    }
}
