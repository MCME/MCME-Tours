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
