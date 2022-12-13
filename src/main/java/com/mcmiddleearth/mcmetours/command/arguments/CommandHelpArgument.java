package com.mcmiddleearth.mcmetours.command.arguments;

import com.mcmiddleearth.command.argument.HelpfulArgumentType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class CommandHelpArgument implements ArgumentType<String>,HelpfulArgumentType {

    private String tooltip = "Which command do you need help with?";

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String o = reader.getString();
        return o;
    }

    @Override
    public void setTooltip(String s) {
        this.tooltip = s;
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }
}
