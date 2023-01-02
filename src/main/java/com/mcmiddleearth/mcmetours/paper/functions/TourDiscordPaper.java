package com.mcmiddleearth.mcmetours.paper.functions;

import com.mcmiddleearth.mcmetours.discord.DiscordChannel;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

/**
 * @author Jubo
 */
public class TourDiscordPaper {

    public static void AnnounceTour(String senderName, String tourName, String discordRoles ,boolean info, String tourInfo){
        String message;
        Guild guild = DiscordSRV.getPlugin().getMainGuild();
        String[] discordRoleSplit = discordRoles.split(",");
        StringBuilder tag = new StringBuilder();
        String discTag;
        for(String discordRole : discordRoleSplit){
            discTag = DiscordUtil.convertMentionsFromNames("@"+discordRole,guild);
            tag.append(discTag).append(", ");
        }
        message = ":MCME: ***"+ tag +senderName
                +" is hosting a new tour*** :MCME:"
                +"\n To join the tour type in game chat: "
                + "```css\n/tour join " + tourName + "```";
        if(info)
            message = message + "__**Tour Description:**__ "+tourInfo;
        sendDiscord(DiscordChannel.ALERTS.getDiscordChannel(), message);
    }

    public static void EndTour(String senderName){
        String message = ":MCME: __**Info:**__ "+ senderName+"´s tour has ended.";
        sendDiscord(DiscordChannel.ALERTS.getDiscordChannel(), message);
    }

    public static void RequestTour(boolean messageBool , String messageRequest, String senderName){
        String message;
        if(messageBool){
            message = ":MCMEGoose: "+senderName+" has requested a tour. Topic: "+messageRequest;
        }else{
            message = ":MCMEGoose: "+senderName+" has requested a tour.";
        }
        sendDiscord(DiscordChannel.GUIDECHAT.getDiscordChannel(), message);
    }

    private static void sendDiscord(String channel, String message){
        DiscordSRV discordPLugin = DiscordSRV.getPlugin();
        TextChannel txtChannel = discordPLugin.getDestinationTextChannelForGameChannelName(channel);
        DiscordUtil.sendMessage(txtChannel,message);
    }
}
