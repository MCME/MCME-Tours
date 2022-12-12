package com.mcmiddleearth.mcmetours.tour;

import com.mcmiddleearth.mcmetours.data.PluginData;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tour {

    private ProxiedPlayer host;
    private List<UUID> players = new ArrayList<>();

    Tour(ProxiedPlayer host){
        this.host = host;
        players.add(host.getUniqueId());
    }


    public ProxiedPlayer getHost() {return host;}
    public List<UUID> getPlayers() { return players;}


    /*
     protected AbstractGame getGame(Player player) {
        AbstractGame game = PluginData.getGame(player);
        if(game==null) {
            sendNotInGameErrorMessage(player);
        }
        return game;
    }
     */
}
