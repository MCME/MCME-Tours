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
     get bungee network running x
      https://www.spigotmc.org/wiki/bungeecord/
      https://papermc.io/downloads#Waterfall
     make first tests with bungee "hello world" plugin  x
     rewrite old tours plugin into bungee
      command-Builder with MCME Brigadier https://github.com/MCME/MCME-Entities/blob/master/src/main/java/com/mcmiddleearth/entities/command/VirtualCommand.java
       and https://github.com/MCME/MCME-CommandParser
      Commands:
       tour request x
       tour join    x
       tour leave   x
       tour help
       tour start   x
       tour end     x
       tour hat
       tour kick    x
       tour refreshments
       tour list    x
       tour chat    x
       ttpa         x
       ttp          x
      OnTabComplete
      all Permissions to Permission.java    x
      Tour-Chat (Chat-Listener)     x
      Tour Start Message            x
      Permissions                   x
      Message when tour runs and player joins       x
      DiscordMessage for tour
      more Functionalities -> ask Guides    x
       Thoughts from me:
        Info command or something what the tour is about
        deactivate guidebook for more immersive tours
        Player ban from tour
        tour broadcast for discord
        normal chat deactivated when in tour chat???
        when in tour chat and you do /g it allows you to chat normal
      Suggestions to implement:
        info command like /tour info and then you can see that somehow
        /tour announce or broadcast for discord
        discord message for /tour request
        /tour host to set a new host
          maybe even co-hosts to set multiple hosts less perms -> still needs a "mother host"
        glow effect for host and co-hosts
        might only allow one tour at once to make messages easier like PlayerJoinEvent
     */

    private static MCMETours instance;
    private static ProxyServer server;

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
}
