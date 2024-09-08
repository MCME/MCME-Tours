package com.mcmiddleearth.mcmetours.proxy.bungee;

import com.mcmiddleearth.base.bungee.AbstractBungeePlugin;
import com.mcmiddleearth.base.bungee.player.BungeeMcmePlayer;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.message.Message;
import com.mcmiddleearth.mcmetours.proxy.bungee.command.ToursPluginCommand;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TcComandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.util.MessageUtil;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.bungee.listener.ChatListener;
import com.mcmiddleearth.mcmetours.proxy.bungee.listener.PlayerListener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo, Eriol_Eandur
 */
public final class McmeToursBungeePlugin extends AbstractBungeePlugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerListener(this,new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this,new PlayerListener());

        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpaCommandHandler("ttpa",this),"ttpa"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpCommandHandler("ttp",this),"ttp"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TourCommandHandler("tour",this),"tour"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TcComandHandler("tc",this),"tc"));

        McmeTours.enable(this);
    }

    @Override
    public void onDisable() {
        for(String tourName: PluginData.getTours()){
            Tour tour = PluginData.getTour(tourName);
            if(tour!=null && tour.getTask())
                tour.getCleanup().cancel();
        }
        PluginData.clearTours();
        McmeTours.disable();
    }

    public static McmeCommandSender wrapCommandSender(CommandSender sender){
        return new BungeeMcmePlayer(((ProxiedPlayer) sender));
    }

    @Override
    public Message getMessagePrefix() {
        return createMessage().add(MessageUtil.PREFIX);
    }
}
