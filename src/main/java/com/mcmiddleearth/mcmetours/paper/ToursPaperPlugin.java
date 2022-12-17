package com.mcmiddleearth.mcmetours.paper;

import com.mcmiddleearth.mcmetours.paper.listener.TourPluginListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ToursPaperPlugin extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this,Channel.MAIN,new TourPluginListener());
    }

    @Override
    public void onDisable() {

    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
