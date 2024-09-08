package com.mcmiddleearth.mcmetours.proxy.core.command.arguments;

import com.mcmiddleearth.base.core.command.argument.AbstractPlayerArgumentType;
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
public class CommandTournameArgument extends AbstractPlayerArgumentType {
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String o = reader.readUnquotedString();
        if(PluginData.getTour(o) != null){
            return o;
        }
        throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("Failed parsing of CommandHostArgument")),
                new LiteralMessage("No tour with this name: " + o));
    }

    @Override
    protected Collection<String> getPlayerSuggestions() {
        return new HashSet<>(PluginData.getTours());
    }
}
