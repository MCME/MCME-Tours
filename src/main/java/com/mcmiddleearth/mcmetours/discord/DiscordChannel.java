package com.mcmiddleearth.mcmetours.discord;

/**
 * @author Jubo
 */
public enum DiscordChannel {

    ALERTS ("global"),
    GUIDECHAT ("global");

    private final String discordChannel;

    DiscordChannel(String discordChannel){
        this.discordChannel = discordChannel;
    }

    public String getDiscordChannel(){
        return discordChannel;
    }
}
