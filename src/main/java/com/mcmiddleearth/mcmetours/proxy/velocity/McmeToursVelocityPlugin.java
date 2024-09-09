package com.mcmiddleearth.mcmetours.proxy.velocity;

import com.google.inject.Inject;
import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.core.message.Message;
import com.mcmiddleearth.base.velocity.AbstractVelocityPlugin;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TcComandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TourCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.command.handler.TtpaCommandHandler;
import com.mcmiddleearth.mcmetours.proxy.core.util.MessageUtil;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.velocity.command.VelocityTourCommand;
import com.mcmiddleearth.mcmetours.proxy.velocity.listener.ChatListener;
import com.mcmiddleearth.mcmetours.proxy.velocity.listener.ConnectionListener;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

/**
 * @author Eriol_Eandur
 */

@Plugin(id = "mcmetours", name = "MCME-Tours", version = "2.0.0",
        url = "https://github.com/MCME/MCME-Tours", description = "Plugin to manage Tours on MCME servers in a Velocity network",
        authors = {"Jubo", "Eriol_Eandur"})
public class McmeToursVelocityPlugin extends AbstractVelocityPlugin {

    @Inject
    public McmeToursVelocityPlugin(Logger logger, ProxyServer proxyServer, @DataDirectory Path dataDirectory) {
        super(logger, proxyServer, dataDirectory);
        McmeTours.setLogger(getMcmeLogger());
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        McmeTours.enable(this);

        getProxyServer().getEventManager().register(this, new ChatListener());
        getProxyServer().getEventManager().register(this, new ConnectionListener());

        registerTourCommand(new TtpaCommandHandler("ttpa",this), Permission.USER.getPermissionNode());
        registerTourCommand(new TtpCommandHandler("ttp",this),null);
        registerTourCommand(new TourCommandHandler("tour",this),null);
        registerTourCommand(new TcComandHandler("tc",this),null);

        getMcmeProxy().getConsole().sendMessage(createMessage().add("Enabled on Velocity proxy!"));
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        McmeTours.disable();
    }

    private void registerTourCommand(AbstractCommandHandler handler, String permission) {
        CommandManager commandManager = getProxyServer().getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder(handler.getCommand()).plugin(this).build();
        commandManager.register(commandMeta, new VelocityTourCommand(handler, permission));
    }

    @Override
    public Message getMessagePrefix() {
        return createMessage().add(MessageUtil.PREFIX);
    }
}

