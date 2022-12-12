package com.mcmiddleearth.mcmetours;

import com.mcmiddleearth.mcmetours.command.TestCommand;
import com.mcmiddleearth.mcmetours.command.ToursPluginCommand;
import com.mcmiddleearth.mcmetours.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.listener.TestListener;
import com.mojang.brigadier.CommandDispatcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

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
       tour request
       tour join
       tour leave
       tour help
       tour start
       tour end
       tour hat
       tour kick
       tour refreshments
       tour list
       tour chat
       tour kick
       ttpa
       ttp
      OnTabComplete
      Tour-Chat (Chat-Listener)
      Tour Start Message
      Permissions
      Message when tour runs and player joins
      NewPlayerPassevent (?) -> passed new player world
      DiscordMessage for tour
      more Functionalities -> ask Guides
       Thoughts from me:
        Info command or something what the tour is about
        deactivate guidebook for more immersive tours

     */

    private static MCMETours instance;
    private static ProxyServer server;
    private final CommandDispatcher<CommandSender> commandDispatcher = new CommandDispatcher<>();
    private final Set<ToursPluginCommand> commands = new HashSet<>();

    @Override
    public void onEnable() {
        server = ProxyServer.getInstance();
        server.getPluginManager().registerListener(this,new TestListener());

        commands.add(new ToursPluginCommand(commandDispatcher,new TtpaCommandHandler("ttpa",commandDispatcher),"ttpa"));
        commands.add(new ToursPluginCommand(commandDispatcher,new TtpCommandHandler("ttp",commandDispatcher),"ttp"));
        commands.add(new ToursPluginCommand(commandDispatcher,new TourCommandHandler("tour",commandDispatcher),"tour"));

        commands.forEach(command -> server.getPluginManager().registerCommand(this,command));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MCMETours getInstance(){
        return instance;
    }
}
