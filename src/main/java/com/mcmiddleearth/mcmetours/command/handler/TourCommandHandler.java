package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.ToursPluginCommand;
import com.mcmiddleearth.mcmetours.command.arguments.CommandHostArgument;
import com.mcmiddleearth.mcmetours.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.command.arguments.CommandStringArgument;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mcmiddleearth.mcmetours.tour.TourHat;
import com.mcmiddleearth.mcmetours.tour.TourRequest;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Jubo
 */
public class TourCommandHandler extends AbstractCommandHandler {

    public TourCommandHandler(String name){
        super(name);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .requires(sender -> (sender instanceof TourCommandSender))
                .then(HelpfulLiteralBuilder.literal("request")
                                .withHelpText("Request Help")
                                .withTooltip("Request Tool")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER))
                                    .executes(context -> doCommand(context.getSource(),"request", null)))
                .then(HelpfulLiteralBuilder.literal("join")
                                .withHelpText("")
                                .withTooltip("")
                                .then(HelpfulRequiredArgumentBuilder.argument("hostname",new CommandHostArgument())
                                        .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.tourRunning()))
                                                .executes(context -> doCommand(context.getSource(),"join",context.getArgument("hostname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("leave")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"leave", null)))
                .then(HelpfulLiteralBuilder.literal("start")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"start",null)))
                .then(HelpfulLiteralBuilder.literal("end")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"end",null)))
                .then(HelpfulLiteralBuilder.literal("hat")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"hat",null)))
                .then(HelpfulLiteralBuilder.literal("kick")
                                .withHelpText("")
                                .withTooltip("")
                        .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("player",new CommandPlayerArgument())
                                        .executes(context -> doCommand(context.getSource(),"kick",context.getArgument("player",String.class)))))
                .then(HelpfulLiteralBuilder.literal("refreshments")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"refreshments",null)))
                .then(HelpfulLiteralBuilder.literal("list")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"list",null)))
                .then(HelpfulLiteralBuilder.literal("chat")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"chat",null)))
                .then(HelpfulLiteralBuilder.literal("info")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("string",new CommandStringArgument()))
                                        .executes(context -> doCommand(context.getSource(),"info",context.getArgument("string",String.class))))
                .then(HelpfulLiteralBuilder.literal("broadcast")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                        .executes(context -> doCommand(context.getSource(),"broadcast",null)));
        return helpfulLiteralBuilder;
    }

    private int doCommand(McmeCommandSender sender, String command, String arg){
        Tour tour;
        ProxiedPlayer player = (ProxiedPlayer) ((TourCommandSender)sender).getCommandSender();
        switch (command) {
            case "request":
                new TourRequest(sender);
                break;
            case "join":
                tour = PluginData.getTour(arg);
                tour.addPlayer(player);
                break;
            case "leave":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.removePlayer((ProxiedPlayer) ((TourCommandSender) sender).getCommandSender());
                break;
            case "start":
                tour = new Tour(player);
                PluginData.addTour(tour);
                break;
            case "end":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.endTour();
                break;
            case "hat":
                new TourHat(sender);
                break;
            case "kick":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.kickPlayer(ProxyServer.getInstance().getPlayer(arg));
                break;
            case "refreshments":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.giveRefreshments();
                break;
            case "list":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.tourList();
                break;
            case "chat":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.activateTourChat((ProxiedPlayer) ((TourCommandSender)sender).getCommandSender());
                break;
            case "info":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.setInfoText(arg);
                break;
            case "broadcast":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.sendDiscordAnnouncement();
                break;
        }
        return 0;
    }

}
