package com.mcmiddleearth.mcmetours.command.arguments;

import com.mcmiddleearth.command.argument.AbstractPlayerArgumentType;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jubo
 */
public class CommandTournameArgument extends AbstractPlayerArgumentType {
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String o = reader.readUnquotedString();
        if(PluginData.getTour(o) != null){
            return o;
        }
        throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("Failed parsing of CommandHostArgument")),
                new LiteralMessage(String.format("No tour with this name: "+o)));
    }

    @Override
    protected Collection<String> getPlayerSuggestions() {
        return PluginData.getTours().stream().collect(Collectors.toSet());
        //return ProxyServer.getInstance().getPlayers().stream().map(ProxiedPlayer::getName).collect(Collectors.toSet());
    }


}
