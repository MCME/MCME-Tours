package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
                .requires(sender -> PluginData.isCoHost((TourCommandSender) sender))
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
