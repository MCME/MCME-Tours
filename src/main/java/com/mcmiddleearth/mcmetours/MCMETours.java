package com.mcmiddleearth.mcmetours;

import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.ToursPluginCommand;
import com.mcmiddleearth.mcmetours.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.listener.ChatListener;
import com.mcmiddleearth.mcmetours.listener.PlayerListener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author Jubo
 */
public final class MCMETours extends Plugin {

    /*
    TODO:
      tour help, working with /tour but need to write the texts
      Cleanup when host leaves? should tour end directly or what???
      correct discord channels
      ttpa kicks me from server: You logged in from another location
       after tour restart worked fine. pretty weird
     */

    private static MCMETours instance;

    @Override
    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerListener(this,new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this,new PlayerListener());

        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpaCommandHandler("ttpa"),"ttpa"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpCommandHandler("ttp"),"ttp"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TourCommandHandler("tour"),"tour"));
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
