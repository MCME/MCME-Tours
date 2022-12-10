package com.mcmiddleearth.mcmetours;

import com.mcmiddleearth.mcmetours.listener.TestListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class MCMETours extends Plugin {

    ProxyServer server;

    @Override
    public void onEnable() {
        server = ProxyServer.getInstance();
        server.getPluginManager().registerListener(this,new TestListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
