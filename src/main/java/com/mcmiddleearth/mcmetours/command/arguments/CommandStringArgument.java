package com.mcmiddleearth.mcmetours.command.arguments;

import com.mcmiddleearth.command.argument.AbstractPlayerArgumentType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Collection;

public class CommandStringArgument extends AbstractPlayerArgumentType {

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException{
        return reader.readString();
    }

    @Override
    protected Collection<String> getPlayerSuggestions() {
        return null;
    }
}
