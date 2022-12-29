package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.arguments.CommandTournameArgument;
import com.mcmiddleearth.mcmetours.command.arguments.CommandPermissionArgument;
import com.mcmiddleearth.mcmetours.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.tour.TourCheck;
import com.mcmiddleearth.mcmetours.util.Permission;
import com.mcmiddleearth.mcmetours.util.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import com.mcmiddleearth.mcmetours.tour.TourHat;
import com.mcmiddleearth.mcmetours.tour.TourRequest;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;

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
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && !PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"request", null))
                                        .then(HelpfulRequiredArgumentBuilder.argument("request",greedyString())
                                            .executes(context -> doCommand(context.getSource(),"request",context.getArgument("request",String.class)))))
                .then(HelpfulLiteralBuilder.literal("check")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender,Permission.USER) && !PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"check",null)))
                .then(HelpfulLiteralBuilder.literal("join")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.tourRunning() && !PluginData.isInTour((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("hostname",new CommandTournameArgument())
                                                .executes(context -> doCommand(context.getSource(),"join",context.getArgument("hostname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("leave")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"leave", null)))
                .then(HelpfulLiteralBuilder.literal("start")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && !PluginData.isInTour((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("tourname",word())
                                        .executes(context -> doCommand(context.getSource(),"start",context.getArgument("tourname",String.class)))))
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
                                    .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                            .executes(context -> doCommand(context.getSource(),"kick",context.getArgument("playername",String.class)))))
                .then(HelpfulLiteralBuilder.literal("refreshments")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender ->  PluginData.isCoHost((TourCommandSender) sender))
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
                                    .then(HelpfulRequiredArgumentBuilder.argument("description",greedyString())
                                        .executes(context -> doCommand(context.getSource(),"info",context.getArgument("description",String.class)))))
                .then(HelpfulLiteralBuilder.literal("announce")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("discordrole",greedyString())
                                        .executes(context -> doCommand(context.getSource(),"announce",context.getArgument("discordrole",String.class)))))
                .then(HelpfulLiteralBuilder.literal("host")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPermissionArgument())
                                        .executes(context -> doCommand(context.getSource(),"host",context.getArgument("playername",String.class)))))
                .then(HelpfulLiteralBuilder.literal("cohost")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulLiteralBuilder.literal("add")
                                        .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                            .executes(context -> doCommand(context.getSource(),"cohostAdd",context.getArgument("playername",String.class)))))
                                    .then(HelpfulLiteralBuilder.literal("remove")
                                        .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                            .executes(context -> doCommand(context.getSource(),"cohostRemove",context.getArgument("playername",String.class))))))
                .then(HelpfulLiteralBuilder.literal("glow")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"glow",null)));
        return helpfulLiteralBuilder;
    }

    private int doCommand(McmeCommandSender sender, String command, String arg){
        Tour tour;
        ProxiedPlayer player = (ProxiedPlayer) ((TourCommandSender)sender).getCommandSender();
        switch (command) {
            case "request":
                TourRequest.informHosts(sender,arg);
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
                tour = new Tour(player,arg);
                PluginData.addTour(tour);
                break;
            case "end":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.endTour();
                break;
            case "hat":
                TourHat.handle(player.getName(), player.getServer().getInfo().getName());
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
            case "announce":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.sendDAnnouncement(arg);
                break;
            case "host":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.setHost(ProxyServer.getInstance().getPlayer(arg));
                break;
            case "cohostAdd":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.setCoHost(ProxyServer.getInstance().getPlayer(arg));
                break;
            case "cohostRemove":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.removeCoHost(ProxyServer.getInstance().getPlayer(arg));
                break;
            case "glow":
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.switchGlow();
                break;
            case "check":
                TourCheck.sendInfos(sender);
                break;
        }
        return 0;
    }
}