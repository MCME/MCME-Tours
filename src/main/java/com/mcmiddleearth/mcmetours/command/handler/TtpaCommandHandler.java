package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mojang.brigadier.CommandDispatcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

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
