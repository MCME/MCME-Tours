package com.mcmiddleearth.mcmetours.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import com.mcmiddleearth.connect.bungee.Handler.TpHandler;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestCommand extends Command implements TabExecutor {

    public TestCommand(){
        super("test");
    }

    public void execute(CommandSender cs,String[] args){
        if(cs instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) cs;
            //ProxiedPlayer player2 = ProxyServer.getInstance().getPlayer("Jubo");
            if(Objects.equals(args[0], "main")){
                player.connect(ProxyServer.getInstance().getServerInfo("main"));
            }else if(Objects.equals(args[0], "lobby")){
                player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
            }else{
                ProxiedPlayer player2 = ProxyServer.getInstance().getPlayer("Jubo");
                player2.sendMessage(new ComponentBuilder(args[0]).color(ChatColor.BLUE).create());
                TpHandler.handle(player.getName(),player2.getServer().getInfo().getName(),player2.getName());
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Test1");
        list.add("Test2");
        return list;
    }
}