package com.mcmiddleearth.mcmetours.proxy.bungee;

import com.mcmiddleearth.base.bungee.AbstractBungeePlugin;
import com.mcmiddleearth.base.bungee.command.BungeeCommand;
import com.mcmiddleearth.base.core.message.Message;
import com.mcmiddleearth.mcmetours.proxy.bungee.listener.ChatListener;
import com.mcmiddleearth.mcmetours.proxy.bungee.listener.PlayerListener;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TcComandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.util.MessageUtil;
import net.md_5.bungee.api.ProxyServer;

/**
 * @author Jubo, Eriol_Eandur
 */
public final class McmeToursBungeePlugin extends AbstractBungeePlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        ProxyServer.getInstance().getPluginManager().registerListener(this,new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this,new PlayerListener());

        ProxyServer.getInstance().getPluginManager()
                .registerCommand(this,new BungeeCommand(this,"ttpa",
                                                                new TtpaCommandHandler("ttpa",this)));
        ProxyServer.getInstance().getPluginManager()
                .registerCommand(this,new BungeeCommand(this,"ttp",
                                                                new TtpCommandHandler("ttp",this)));
        ProxyServer.getInstance().getPluginManager()
                .registerCommand(this,new BungeeCommand(this,"tour",
                                                                new TourCommandHandler("tour",this)));
        ProxyServer.getInstance().getPluginManager()
                .registerCommand(this,new BungeeCommand(this,"tc",
                                                                new TcComandHandler("tc",this)));

        McmeTours.enable(this);
    }

    @Override
    public void onDisable() {
        McmeTours.disable();
    }

    @Override
    public Message getMessagePrefix() {
        return createMessage().add(MessageUtil.PREFIX);
    }
}
