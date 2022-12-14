package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mojang.brigadier.CommandDispatcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TtpCommandHandler extends AbstractCommandHandler {

    public TtpCommandHandler(String name){
        super(name);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .withHelpText("teleports to Host")
                .withTooltip("")
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
                tour.teleportPlayer(ProxyServer.getInstance().getPlayer(player));
            }else{
                tour.TeleportToHost((ProxiedPlayer) ((TourCommandSender) sender).getCommandSender());
            }
        }
        return 0;
    }

}
