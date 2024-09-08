package com.mcmiddleearth.mcmetours.proxy.core.command.handler;

import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.base.core.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.base.core.plugin.McmePlugin;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TcComandHandler extends AbstractCommandHandler {

    public TcComandHandler(String name, McmePlugin plugin){super(name, plugin);}

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder){
        helpfulLiteralBuilder
                .requires(sender -> (sender instanceof McmeProxyPlayer player)
                                        && (PluginData.hasPermission(sender, Permission.USER)
                                        && PluginData.isInTour(player)))
                .then(HelpfulRequiredArgumentBuilder.argument("text",greedyString())
                .executes(context -> sendToTourChat(context.getSource(),context.getArgument("text",String.class))));
        return helpfulLiteralBuilder;
    }

    private int sendToTourChat(McmeCommandSender sender, String message){
        McmeProxyPlayer player = (McmeProxyPlayer) sender;
        Tour tour = PluginData.getTour(player);
        if(tour != null ){
            tour.tourChat(player, message);
        }
        return 0;
    }
}
