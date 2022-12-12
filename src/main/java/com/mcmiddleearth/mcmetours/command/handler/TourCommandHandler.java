package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.mcmetours.tourCommand.RequestTourCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TourCommandHandler extends AbstractCommandHandler implements TabExecutor {

    public TourCommandHandler(String name, CommandDispatcher<CommandSender> dispatcher){
        super(name);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .then(HelpfulLiteralBuilder.literal("request"));
        return helpfulLiteralBuilder;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
