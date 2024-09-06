package com.mcmiddleearth.mcmetours.proxy.core.command.arguments;

import com.mcmiddleearth.command.argument.AbstractPlayerArgumentType;
import com.mcmiddleearth.mcmetours.proxy.core.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jubo
 */
public class CommandPlayerArgument extends AbstractPlayerArgumentType {

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String o = reader.readUnquotedString();
        TourCommandSender sender = new TourCommandSender(ProxyServer.getInstance().getPlayer(o));
        if(PluginData.getTour(sender) != null){
            return o;
        }
        throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("Failed parsing of CommandPlayerArgument")),
                new LiteralMessage("Player not inside a tour: " + o));
    }

    @Override
    protected Collection<String> getPlayerSuggestions() {
        return ProxyServer.getInstance().getPlayers().stream().map(ProxiedPlayer::getName).collect(Collectors.toSet());
    }
}
