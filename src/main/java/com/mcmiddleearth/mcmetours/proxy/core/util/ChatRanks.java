package com.mcmiddleearth.mcmetours.proxy.core.util;

import com.mcmiddleearth.base.core.message.MessageColor;

/**
 * @author Jubo
 */
public enum ChatRanks {

    HOST (MessageColor.DARK_AQUA, "<Tour Host> "),
    PARTICIPANT (MessageColor.GOLD, "<Participant> "),
    BADGEHOLDER (MessageColor.BLUE, "<Participant> ");

    private final String chatPrefix;
    private final MessageColor chatColor;

    ChatRanks(MessageColor chatColor, String chatPrefix){
        this.chatColor = chatColor;
        this.chatPrefix = chatPrefix;
    }

    public String getChatPrefix(){
        return chatPrefix;
    }

    public MessageColor getChatColor() {
        return chatColor;
    }
}