package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mojang.brigadier.CommandDispatcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TtpCommandHandler extends AbstractCommandHandler implements TabExecutor {

    public TtpCommandHandler(String name, CommandDispatcher<CommandSender> dispatcher){
        super(name);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        return helpfulLiteralBuilder;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
