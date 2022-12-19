package com.mcmiddleearth.mcmetours.paper.functions;

import com.mcmiddleearth.mcmetours.discord.DiscordChannel;
import com.mcmiddleearth.mcmetours.paper.Channel;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;

/**
 * @author Jubo
 */
public class TourDiscordPaper {

    public static void AnnounceTour(String senderName, String tourName, boolean info, String tourInfo){
        String message;
        Guild guild = DiscordSRV.getPlugin().getMainGuild();
        String discTag = DiscordUtil.convertMentionsFromNames("@Tourist",guild);
        message = ":MCME: ***"+ discTag + ", "+senderName
                +" is hosting a new tour*** :MCME:"
                +"\n To join the tour type in game chat: "
                + "```css\n/tour join " + tourName + "```";
        if(info)
            message = message + "__**Tour Description:**__ "+info;
        sendDiscord(DiscordChannel.ALERTS.getDiscordChannel(), message);
    }

    public static void EndTour(String senderName){
        String message = ":MCME: __**Info:**__ "+ senderName+"´s tour has ended.";
        sendDiscord(DiscordChannel.ALERTS.getDiscordChannel(), message);
    }

    public static void RequestTour(boolean messageBool , String messageRequest, String senderName){
        String message;
        if(messageBool){
            message = senderName+" has requested a tour. There are currently no badge-holders online to help them. Description: "+messageRequest;
        }else{
            message = senderName+" has requested a tour. There are currently no badge-holders online to help them.";
        }
        sendDiscord(DiscordChannel.GUIDECHAT.getDiscordChannel(), message);
    }

    private static void sendDiscord(String channel, String message){
        DiscordSRV discordPLugin = DiscordSRV.getPlugin();
        TextChannel txtChannel = discordPLugin.getDestinationTextChannelForGameChannelName(channel);
        DiscordUtil.sendMessage(txtChannel,message);
    }
}
