package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TtpCommandHandler extends AbstractCommandHandler {

    public TtpCommandHandler(String name){
        super(name);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender))
                    .executes(context -> teleportToHost(context.getSource(),null))
                        .then(HelpfulRequiredArgumentBuilder.argument("player",new CommandPlayerArgument())
                            .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) ))
                                .executes(context -> teleportToHost(context.getSource(),context.getArgument("player",String.class))));
        return helpfulLiteralBuilder;
    }

    private int teleportToHost(McmeCommandSender sender, String player){
        Tour tour = PluginData.getTour((TourCommandSender) sender);
        if(tour != null){
            if(PluginData.isHost((TourCommandSender) sender)){
                tour.teleportPlayer(ProxyServer.getInstance().getPlayer(player),(ProxiedPlayer) ((TourCommandSender) sender).getCommandSender());
            }else{
                tour.TeleportToHost((ProxiedPlayer) ((TourCommandSender) sender).getCommandSender());
            }
        }
        return 0;
    }

}
