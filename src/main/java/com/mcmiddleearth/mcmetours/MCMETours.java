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
      Commands:
       tour help, working with /tour but need to write the texts
       tour hat     x
       tour refreshments        x
      OnTabComplete     x
      DiscordMessage for tour   x
        needs to be tested      x
      Suggestions to implement:
        info command like /tour info and then you can see that somehow  -> over Discord     x
        /tour announce or broadcast for discord x
        discord message for /tour request       x
        /tour host to set a new host        x
          maybe even co-hosts to set multiple hosts less perms -> still needs a "mother host"       x
        glow effect for host and co-hosts       x
      Bukkit Part for refreshments and hat and probably glow    x
      For Tour join: only show with Arguments who really has a tour     x
      Invalid synntax for cohost and host command   x
      Cleanup when host leaves? should tour end directly or what???
      tour check        x
      tour start <name> insteead of playername as name  x
      New Suggestions:
       name a tour (something specific) x
       /tour request <what is requested>    x
       /tour check (maybe) ??       kinda    x
       when joining /discord message        x
       Permission for world tp (adventurers arent allowed in FB)        x
       correct discord channels
       tour check command       x
        shows running tours and which guides/badgeholders are online        x
      tour hat message
      remove cohosts
      check if someone is already cohost        x
      check if host is already host             x
      Bugs:
       seems to be connected to the TourCommandSender need to look at that      x
        Kicked when doing /ttpa in moria
        tour refreshments doesnt work
        error when doing /ttpa on main
       hightlight about section in announcement     x
       when switching host remove cohost from other one     x
       cohost ttpa should teleport to sender not host   x
       when setting host check if persin already cohost     x
     */

    private static MCMETours instance;

    @Override
    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerListener(this,new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this,new PlayerListener());


        //commands.add(new ToursPluginCommand(commandDispatcher,new TtpaCommandHandler("ttpa",commandDispatcher),"ttpa"));
        //commands.add(new ToursPluginCommand(commandDispatcher,new TtpCommandHandler("ttp",commandDispatcher),"ttp"));
        //commands.add(new ToursPluginCommand(commandDispatcher,new TourCommandHandler("tour",commandDispatcher),"tour"));

        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpaCommandHandler("ttpa"),"ttpa"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TtpCommandHandler("ttp"),"ttp"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new ToursPluginCommand(new TourCommandHandler("tour"),"tour"));

        //commands.forEach(command -> server.getPluginManager().registerCommand(this,command));
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
