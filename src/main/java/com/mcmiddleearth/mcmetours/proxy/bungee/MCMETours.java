package com.mcmiddleearth.mcmetours.proxy.bungee;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.proxy.core.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.proxy.bungee.command.ToursPluginCommand;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TcComandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.bungee.listener.ChatListener;
import com.mcmiddleearth.mcmetours.proxy.bungee.listener.PlayerListener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author Jubo
 */
public final class MCMETours extends Plugin {

    private static MCMETours instance;

    @Override
    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerListener(this,new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this,new PlayerListener());

        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpaCommandHandler("ttpa"),"ttpa"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpCommandHandler("ttp"),"ttp"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TourCommandHandler("tour"),"tour"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TcComandHandler("tc"),"tc"));
    }

    @Override
    public void onDisable() {
        for(String tourName: PluginData.getTours()){
            Tour tour = PluginData.getTour(tourName);
            if(tour.getTask())
                tour.getCleanup().cancel();
        }
        PluginData.clearTours();
    }

    public static McmeCommandSender wrapCommandSender(CommandSender sender){
        return TourCommandSender.getOrCreateMcmePlayer((ProxiedPlayer) sender);
    }

    public static MCMETours getInstance(){
        return instance;
    }
}
