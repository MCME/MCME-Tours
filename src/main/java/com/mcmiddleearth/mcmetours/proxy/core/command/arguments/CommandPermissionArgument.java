package com.mcmiddleearth.mcmetours.proxy.core.command.arguments;

import com.mcmiddleearth.base.core.command.argument.AbstractPlayerArgumentType;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Jubo, Eriol_Eandur
 */
public class CommandPermissionArgument extends AbstractPlayerArgumentType {

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException{
        String o = reader.readUnquotedString();
        McmeProxyPlayer sender = McmeTours.getProxy().getPlayer(o);
        if(PluginData.getTour(sender) != null && PluginData.hasPermission(sender, Permission.HOST)){
            return o;
        }
        throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("Failed parsing of CommandPlayerArgument")),
                new LiteralMessage("Player not inside a tour or has the correct permission: " + o));
    }

    @Override
    protected Collection<String> getPlayerSuggestions() {
        return new HashSet<>(PluginData.getHostPerms());
    }
}
