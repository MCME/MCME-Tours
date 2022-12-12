package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mojang.brigadier.CommandDispatcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TtpaCommandHandler extends AbstractCommandHandler{

    String name;

    public TtpaCommandHandler(String name){
        super(name);
        this.name = name;
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .withHelpText("TestText1")
                .withTooltip("TestText2")
                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                .executes(context -> sendInfoMessage(context.getSource(),"TestInfo"));
        return helpfulLiteralBuilder;
    }


                /*
                .withHelpText("TestText1")
                .withTooltip("TestText2")
                .requires(sender -> PluginData.hasPermission((ProxiedPlayer) sender, Permission.HOST))
                .executes(context -> sendInfoMessage(context.getSource(),"TestInfo"));
                //.requires(sender -> ) Tour is running

                 */
    private int sendInfoMessage(McmeCommandSender sender, String text){
        sender.sendMessage(new ComponentBuilder(text).create());
        return 0;
    }
}
