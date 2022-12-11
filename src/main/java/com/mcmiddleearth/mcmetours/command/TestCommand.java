package com.mcmiddleearth.mcmetours.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import com.mcmiddleearth.connect.bungee.Handler.TpposHandler;

import java.util.Objects;

public class TestCommand extends Command {

    public TestCommand(){
        super("test");
    }

    public void execute(CommandSender cs,String[] args){
        if(cs instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) cs;
            if(Objects.equals(args[0], "main")){
                player.connect(ProxyServer.getInstance().getServerInfo("main"));
            }else if(Objects.equals(args[0], "lobby")){
                player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
            }else{
                player.sendMessage(new ComponentBuilder(args[0]).color(ChatColor.BLUE).create());
                TpposHandler.handle(player.toString(),ProxyServer.getInstance().getServerInfo("main").toString(),ProxyServer.getInstance().getServerInfo("main").toString(),"0,0,0","Test3");
            }
        }
    }
}
