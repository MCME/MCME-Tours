package com.mcmiddleearth.mcmetours.proxy.core.command.handler;

import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.base.core.plugin.McmePlugin;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TtpaCommandHandler extends AbstractCommandHandler{

    public TtpaCommandHandler(String name, McmePlugin plugin){
        super(name, plugin);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .requires(sender -> (sender instanceof McmeProxyPlayer player)
                                        && (PluginData.hasPermission(sender, Permission.USER))
                                        && PluginData.isCoHost(player))
                    .executes(context -> teleportAll(context.getSource()));
        return helpfulLiteralBuilder;
    }

    private int teleportAll(McmeCommandSender sender){
        McmeProxyPlayer player = (McmeProxyPlayer) sender;
        Tour tour = PluginData.getTour(player);
        if(tour != null){
            tour.teleportAll(player);
        }
        return 0;
    }
}
