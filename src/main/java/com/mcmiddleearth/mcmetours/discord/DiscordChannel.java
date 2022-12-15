package com.mcmiddleearth.mcmetours.discord;

/**
 * @author Jubo
 */
public enum DiscordChannel {

    ALERTS ("#\uD83D\uDCE2-alerts"),
    SERVERCHAT ("#serverchat"),
    GUIDECHAT ("guidechat");

    private final String discordChannel;

    DiscordChannel(String discordChannel){
        this.discordChannel = discordChannel;
    }

    public String getDiscordChannel(){
        return discordChannel;
    }
}
