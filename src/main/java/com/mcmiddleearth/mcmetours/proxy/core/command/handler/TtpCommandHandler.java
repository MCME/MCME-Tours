package com.mcmiddleearth.mcmetours.proxy.core.command.handler;

import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.base.core.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.base.core.plugin.McmePlugin;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TtpCommandHandler extends AbstractCommandHandler {

    public TtpCommandHandler(String name, McmePlugin plugin){
        super(name, plugin);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .requires(sender -> (sender instanceof McmeProxyPlayer player)
                                        && PluginData.hasPermission(sender, Permission.USER)
                                        && PluginData.isInTour(player))
                    .executes(context -> teleportToHost(context.getSource(),null))
                        .then(HelpfulRequiredArgumentBuilder.argument("player",new CommandPlayerArgument())
                            .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST) ))
                                .executes(context -> teleportToHost(context.getSource(),context.getArgument("player",String.class))));
        return helpfulLiteralBuilder;
    }

    private int teleportToHost(McmeCommandSender sender, String player){
        McmeProxyPlayer senderPlayer = (McmeProxyPlayer) sender;
        Tour tour = PluginData.getTour(senderPlayer);
        if(tour != null){
            if(PluginData.isHost(senderPlayer)){
                tour.teleportPlayer(McmeTours.getProxy().getPlayer(player),senderPlayer);
            }else{
                tour.TeleportToHost(senderPlayer);
            }
        }
        return 0;
    }

}
