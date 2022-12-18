package com.mcmiddleearth.mcmetours;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.ToursPluginCommand;
import com.mcmiddleearth.mcmetours.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.listener.ChatListener;
import com.mcmiddleearth.mcmetours.listener.PlayerListener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jubo
 */
public final class MCMETours extends Plugin {

    /*
    TODO:
      Commands:
       tour help, working with /tour but need to write the texts
       tour hat
       tour refreshments
      OnTabComplete
      DiscordMessage for tour
      Suggestions to implement:
        info command like /tour info and then you can see that somehow
        /tour announce or broadcast for discord
        discord message for /tour request
        /tour host to set a new host
          maybe even co-hosts to set multiple hosts less perms -> still needs a "mother host"
        glow effect for host and co-hosts
        might only allow one tour at once to make messages easier like PlayerJoinEvent
      Bukkit Part for refreshments and hat and probably glow
      For Tour join: only show with Arguments who really has a tour     x
      Invalid synntax for cohost and host command   x
      Cleanup when host leaves? should tour end directly or what???
     */

    private static MCMETours instance;
    private static ProxyServer server;
    private static int connectDelay = 200;

    @Override
    public void onEnable() {
        instance = this;
        server = ProxyServer.getInstance();
        server.getPluginManager().registerListener(this,new ChatListener());
        server.getPluginManager().registerListener(this,new PlayerListener());


        //commands.add(new ToursPluginCommand(commandDispatcher,new TtpaCommandHandler("ttpa",commandDispatcher),"ttpa"));
        //commands.add(new ToursPluginCommand(commandDispatcher,new TtpCommandHandler("ttp",commandDispatcher),"ttp"));
        //commands.add(new ToursPluginCommand(commandDispatcher,new TourCommandHandler("tour",commandDispatcher),"tour"));

        server.getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpaCommandHandler("ttpa"),"ttpa"));
        server.getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpCommandHandler("ttp"),"ttp"));
        server.getPluginManager().registerCommand(this,new ToursPluginCommand(new TourCommandHandler("tour"),"tour"));

        //commands.forEach(command -> server.getPluginManager().registerCommand(this,command));
    }

    @Override
    public void onDisable() {
        PluginData.clearTours();
    }

    public static McmeCommandSender wrapCommandSender(CommandSender sender){
        return TourCommandSender.getOrCreateMcmePlayer((ProxiedPlayer) sender);
    }

    public static MCMETours getInstance(){
        return instance;
    }

    public static int getConnectDelay(){
        return connectDelay;
    }
}
