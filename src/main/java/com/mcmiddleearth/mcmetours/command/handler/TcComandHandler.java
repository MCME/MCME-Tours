package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class TcComandHandler extends AbstractCommandHandler {

    public TcComandHandler(String name){super(name);}

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder){
        helpfulLiteralBuilder
                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                .then(HelpfulRequiredArgumentBuilder.argument("text",greedyString())
                .executes(context -> sendToTourChat(context.getSource(),context.getArgument("text",String.class))));
        return helpfulLiteralBuilder;
    }

    private int sendToTourChat(McmeCommandSender sender, String message){
        Tour tour = PluginData.getTour((TourCommandSender) sender);
        if(tour != null ){
            tour.tourChat((ProxiedPlayer) ((TourCommandSender) sender).getCommandSender(),message);
        }
        return 0;
    }
}
