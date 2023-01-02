package com.mcmiddleearth.mcmetours.paper;

import com.mcmiddleearth.mcmetours.paper.listener.TourPluginListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Jubo
 */
public class ToursPaperPlugin extends JavaPlugin {

    private static JavaPlugin instance;
    private static String announcementChannel;
    private static String requestChannel;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this,Channel.MAIN,new TourPluginListener());
        loadConfig();
    }

    @Override
    public void onDisable() {}

    private void loadConfig(){
        ConfigurationSection config = this.getConfig().getConfigurationSection("discordChannel");
        announcementChannel = config.getString("announcement");
        requestChannel = config.getString("request");
    }

    public static String getRequestChannel(){return requestChannel;}
    public static String getAnnouncementChannel(){return announcementChannel;}
    public static JavaPlugin getInstance() {
        return instance;
    }
}
