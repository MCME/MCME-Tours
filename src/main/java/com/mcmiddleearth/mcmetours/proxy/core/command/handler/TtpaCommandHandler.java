package com.mcmiddleearth.mcmetours.proxy.core.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.mcmetours.proxy.core.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;

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
                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER)) && PluginData.isCoHost((TourCommandSender) sender))
                    .executes(context -> teleportAll(context.getSource()));
        return helpfulLiteralBuilder;
    }

    private int teleportAll(McmeCommandSender sender){
        Tour tour = PluginData.getTour((TourCommandSender) sender);
        if(tour != null){
            tour.teleportAll((ProxiedPlayer) ((TourCommandSender) sender).getCommandSender());
        }
        return 0;
    }
}
