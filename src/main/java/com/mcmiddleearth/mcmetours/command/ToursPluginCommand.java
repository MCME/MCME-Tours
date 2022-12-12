package com.mcmiddleearth.mcmetours.command;

import com.google.common.base.Joiner;
import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class ToursPluginCommand extends Command {

    private final CommandDispatcher<CommandSender> commandDispatcher;

    public ToursPluginCommand(CommandDispatcher<CommandSender> commandDispatcher, AbstractCommandHandler handler, String name) {
        super(name);
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            ProxyServer.getInstance().getPlayer("Jubo").sendMessage(new ComponentBuilder("Test").create());
            String message = String.format("%s %s", getName(), Joiner.on(' ').join(args)).trim();
            ParseResults<CommandSender> result = commandDispatcher.parse(message, sender);
            result.getExceptions().entrySet().stream()
                    .findFirst().ifPresent(error -> sender.sendMessage(new ComponentBuilder(error.getValue().getMessage()).color(PluginData.getMessageUtil().getError()).create()));
            if (result.getExceptions().isEmpty()) {
                if (result.getContext().getNodes().size() > 0
                        && (result.getContext().getCommand() == null
                        || result.getContext().getRange().getEnd() < result.getReader().getString().length())) {

                } else {
                    ProxyServer.getInstance().getPlayer("Jubo").sendMessage(new ComponentBuilder("Test").create());
                    commandDispatcher.execute(result);
                }
            }
        } catch (CommandSyntaxException e){

        }
    }
}
