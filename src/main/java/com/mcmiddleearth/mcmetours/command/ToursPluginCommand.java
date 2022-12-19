package com.mcmiddleearth.mcmetours.command;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.SimpleTabCompleteRequest;
import com.mcmiddleearth.command.TabCompleteRequest;
import com.mcmiddleearth.mcmetours.MCMETours;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * @author Jubo
 */
public class ToursPluginCommand extends Command implements TabExecutor {


    private AbstractCommandHandler handler;
    private String name;

    public ToursPluginCommand(AbstractCommandHandler handler,String name) {
        super(name);
        this.name = name;
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        McmeCommandSender wrappedSender = MCMETours.wrapCommandSender(sender);
        handler.execute(wrappedSender,args);
    }


    // java.lang.NullPointerException: Cannot invoke "java.lang.Iterable.iterator()" because the return value of "net.md_5.bungee.api.plugin.TabExecutor.onTabComplete(net.md_5.bungee.api.CommandSender, String[])" is null

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        TabCompleteRequest request = new SimpleTabCompleteRequest(MCMETours.wrapCommandSender(sender),String.format("/%s %s",name,String.join(" ",args)));
        //String test = request.getCursor().substring(1);
        //sender.sendMessage(new ComponentBuilder(test).create());

        handler.onTabComplete(request);
        return request.getSuggestions();
    }
}
