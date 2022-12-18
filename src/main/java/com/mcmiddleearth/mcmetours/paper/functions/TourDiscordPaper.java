package com.mcmiddleearth.mcmetours.paper.functions;

import com.mcmiddleearth.mcmetours.discord.DiscordChannel;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

public class TourDiscordPaper {

    public static void sendDiscord(String channel, String message){
        DiscordSRV discordPLugin = DiscordSRV.getPlugin();
        TextChannel txtChannel = discordPLugin.getDestinationTextChannelForGameChannelName(channel);
        DiscordUtil.sendMessage(txtChannel,message);
    }
}
