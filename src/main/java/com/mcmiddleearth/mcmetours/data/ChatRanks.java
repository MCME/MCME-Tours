package com.mcmiddleearth.mcmetours.data;

import net.md_5.bungee.api.ChatColor;

public enum ChatRanks {

    HOST (ChatColor.DARK_AQUA+"<Tour Host> "),
    PARTICIPANT (ChatColor.WHITE+"<Participant> "),
    BADGEHOLDER (ChatColor.BLUE+"<Participant> ");

    private final String chatPrefix;

    ChatRanks(String chatPrefix){
        this.chatPrefix = chatPrefix;
    }

    public String getChatPrefix(){
        return chatPrefix;
    }
}

