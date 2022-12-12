package com.mcmiddleearth.mcmetours.command;

import com.google.common.base.Joiner;
import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.MCMETours;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class ToursPluginCommand extends Command implements TabExecutor {


    AbstractCommandHandler handler;

    public ToursPluginCommand(AbstractCommandHandler handler, String name) {
        super(name);
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        McmeCommandSender wrappedSender = MCMETours.wrapCommandSender(sender);
        handler.execute(wrappedSender,args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
