package com.mcmiddleearth.mcmetours;

import com.mcmiddleearth.mcmetours.command.TestCommand;
import com.mcmiddleearth.mcmetours.listener.TestListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class MCMETours extends Plugin {

    /*
    TODO:
     get bungee network running x
      https://www.spigotmc.org/wiki/bungeecord/
      https://papermc.io/downloads#Waterfall
     make first tests with bungee "hello world" plugin  x
     rewrite old tours plugin into bungee
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

    ProxyServer server;

    @Override
    public void onEnable() {
        server = ProxyServer.getInstance();
        server.getPluginManager().registerListener(this,new TestListener());
        server.getPluginManager().registerCommand(this,new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
