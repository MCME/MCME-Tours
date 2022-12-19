package com.mcmiddleearth.mcmetours.command.arguments;

import com.mcmiddleearth.command.argument.AbstractPlayerArgumentType;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.md_5.bungee.api.ProxyServer;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jubo
 */
public class CommandPermissionArgument extends AbstractPlayerArgumentType {

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException{
        String o = reader.readUnquotedString();
        TourCommandSender sender = new TourCommandSender(ProxyServer.getInstance().getPlayer(o));
        if(PluginData.getTour(sender) != null && PluginData.hasPermission(sender, Permission.HOST)){
            return o;
        }
        throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("Failed parsing of CommandPlayerArgument")),
                new LiteralMessage(String.format("Player not inside a tour or has the correct permission: "+o)));
    }

    @Override
    protected Collection<String> getPlayerSuggestions() {
        return PluginData.getHostPerms().stream().collect(Collectors.toSet());
    }
}
