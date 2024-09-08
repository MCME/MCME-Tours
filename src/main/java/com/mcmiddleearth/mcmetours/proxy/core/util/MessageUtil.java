package com.mcmiddleearth.mcmetours.proxy.core.util;

import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.message.McmeColors;
import com.mcmiddleearth.base.core.message.Message;
import com.mcmiddleearth.base.core.message.MessageClickEvent;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;

/**
 * @author Jubo, Eriol_Eandur
 */
public class MessageUtil {

    public static final String PREFIX = "[MCME-Tours] ";

    public static Message runningTourInfo() {
        return McmeTours.getPlugin().createInfoMessage()
                .add("A tour is currently running. ")
                .add(McmeTours.getPlugin().createMessage()
                        .add("Click here", McmeColors.INFO_STRESSED)
                        .addClick(new MessageClickEvent(MessageClickEvent.Action.RUN_COMMAND, "/tour check")))
                .add(" for more information.");
    }

    public static Message tourRequest(McmeProxyPlayer player, String tourTheme) {
        Message message = McmeTours.getPlugin().createInfoMessage().add(player.getName()+" has requested a tour!");
        if(tourTheme != null){
            message.add(" About: ").add(tourTheme,McmeColors.INFO_STRESSED);
        }
        return message;
    }

    public void sendBroadcastMessage(String message){
        McmeTours.getProxy().broadcast(McmeTours.getPlugin().createInfoMessage().add(message));
        /*for(ProxiedPlayer player: ProxyServer.getInstance().getPlayers()){
            player.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.INFO).create());
        }*/
    }

    public void sendErrorMessage(McmeCommandSender sender, String message){
        sender.sendMessage(McmeTours.errorMessage().add(message));
        //sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.ERROR).create());
    }

    public void sendNotPossible(McmeCommandSender sender){
        sender.sendMessage(McmeTours.errorMessage("This is currently not possible."));
        //sender.sendMessage(new ComponentBuilder(PREFIX+"This is currently not possible.").color(Style.ERROR).create());
    }

    public void sendInfoMessage(McmeCommandSender sender, String message){
        sender.sendMessage(McmeTours.infoMessage(message));
        //sender.sendMessage(new ComponentBuilder(PREFIX+message).color(Style.INFO).create());
    }

}
