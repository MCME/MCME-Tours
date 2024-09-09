package com.mcmiddleearth.mcmetours.proxy.bungee.command;

import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.command.SimpleTabCompleteRequest;
import com.mcmiddleearth.base.core.command.TabCompleteRequest;
import com.mcmiddleearth.mcmetours.proxy.bungee.McmeToursBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * @author Jubo, Eriol_Eandur
 */
public class BungeeTourCommand extends Command implements TabExecutor {


    private final AbstractCommandHandler handler;
    private final String name;

    public BungeeTourCommand(AbstractCommandHandler handler, String name) {
        super(name);
        this.name = name;
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        McmeCommandSender wrappedSender = McmeToursBungeePlugin.wrapCommandSender(sender);
        handler.execute(wrappedSender,args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        TabCompleteRequest request = new SimpleTabCompleteRequest(McmeToursBungeePlugin.wrapCommandSender(sender),String.format("/%s %s",name,String.join(" ",args)));
        handler.onTabComplete(request);
        return request.getSuggestions();
    }
}
