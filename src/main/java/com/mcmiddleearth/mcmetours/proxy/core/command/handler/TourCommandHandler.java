package com.mcmiddleearth.mcmetours.proxy.core.command.handler;

import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.core.command.McmeCommandSender;
import com.mcmiddleearth.base.core.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.base.core.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.base.core.message.McmeColors;
import com.mcmiddleearth.base.core.message.MessageStyle;
import com.mcmiddleearth.base.core.player.McmeProxyPlayer;
import com.mcmiddleearth.base.core.plugin.McmePlugin;
import com.mcmiddleearth.mcmetours.proxy.core.McmeTours;
import com.mcmiddleearth.mcmetours.proxy.core.command.arguments.CommandTournameArgument;
import com.mcmiddleearth.mcmetours.proxy.core.command.arguments.CommandPermissionArgument;
import com.mcmiddleearth.mcmetours.proxy.core.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.proxy.core.tour.TourCheck;
import com.mcmiddleearth.mcmetours.proxy.core.util.Permission;
import com.mcmiddleearth.mcmetours.proxy.core.util.PluginData;
import com.mcmiddleearth.mcmetours.proxy.core.tour.Tour;
import com.mcmiddleearth.mcmetours.proxy.core.tour.TourHat;
import com.mcmiddleearth.mcmetours.proxy.core.tour.TourRequest;

import java.util.logging.Logger;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;

/**
 * @author Jubo, Eriol_Eandur
 */
public class TourCommandHandler extends AbstractCommandHandler {

    public TourCommandHandler(String name, McmePlugin plugin){
        super(name, plugin);
    }

    @Override
    protected HelpfulLiteralBuilder createCommandTree(HelpfulLiteralBuilder helpfulLiteralBuilder) {
        helpfulLiteralBuilder
                .requires(sender -> (sender instanceof McmeProxyPlayer))
                .then(HelpfulLiteralBuilder.literal("request")
                                .withHelpText("Submit a request for a tour.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour request <topic>",McmeColors.TOOLTIP)
                                        .add(": Sends a tour request to all guides and people with tour-badge on the server and the discord."
                                                +"The topic is optional and the request can be send without."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.USER) && !PluginData.isInTour((McmeProxyPlayer) sender)))
                                    .executes(context -> doCommand(context.getSource(),"request", null))
                                        .then(HelpfulRequiredArgumentBuilder.argument("topic",greedyString())
                                            .executes(context -> doCommand(context.getSource(),"request",context.getArgument("topic",String.class)))))
                .then(HelpfulLiteralBuilder.literal("check")
                                .withHelpText("List all current tours.")
                                .requires(sender -> (PluginData.hasPermission(sender,Permission.USER) && !PluginData.isInTour((McmeProxyPlayer) sender)))
                                    .executes(context -> doCommand(context.getSource(),"check",null)))
                .then(HelpfulLiteralBuilder.literal("join")
                                .withHelpText("Join one of the running tours.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                                .add("/tour join <name of tour>",McmeColors.TOOLTIP)
                                                .add(": Joins the specific tour you want to join."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.USER) && !PluginData.isInTour((McmeProxyPlayer) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("tourname",new CommandTournameArgument())
                                                .executes(context -> doCommand(context.getSource(),"join",context.getArgument("tourname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("leave")
                                .withHelpText("Leave the currently joined tour.")
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.USER)))
                                    .executes(context -> doCommand(context.getSource(),"leave", null)))
                .then(HelpfulLiteralBuilder.literal("start")
                                .withHelpText("Start a new tour.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                                .add("/tour start <name of tour>",McmeColors.TOOLTIP)
                                                .add(": Starts a tour with a set name. The name can only be one word."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST) && !PluginData.isInTour((McmeProxyPlayer) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("tourname",word())
                                        .executes(context -> doCommand(context.getSource(),"start",context.getArgument("tourname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("end")
                                .withHelpText("End your current tour.")
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                    .executes(context -> doCommand(context.getSource(),"end",null)))
                .then(HelpfulLiteralBuilder.literal("stop")
                        .withHelpText("Stop your current tour.")
                        .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                        .executes(context -> doCommand(context.getSource(),"end",null)))
                .then(HelpfulLiteralBuilder.literal("hat")
                                .withHelpText("Set an item as your hat.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                                .add("/tour hat",McmeColors.TOOLTIP)
                                                .add(": Sets the item in your main hand as your hat. It can be used outside of tours."))
                                .requires(sender -> PluginData.hasPermission(sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"hat",null)))
                .then(HelpfulLiteralBuilder.literal("kick")
                                .withHelpText("Kick a player from your tour.")
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                            .executes(context -> doCommand(context.getSource(),"kick",context.getArgument("playername",String.class)))))
                .then(HelpfulLiteralBuilder.literal("refreshments")
                                .withHelpText("Give all participants refreshments.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour refreshments",McmeColors.TOOLTIP)
                                        .add(": Gives all tourists tea and cookies."))
                                .requires(sender ->  (PluginData.hasPermission(sender, Permission.USER)))
                                    .executes(context -> doCommand(context.getSource(),"refreshments",null)))
                .then(HelpfulLiteralBuilder.literal("list")
                                .withHelpText("List all participants of your tour.")
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.USER)))
                                    .executes(context -> doCommand(context.getSource(),"list",null)))
                .then(HelpfulLiteralBuilder.literal("chat")
                                .withHelpText("Switch the tour-chat on and off.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour chat",McmeColors.TOOLTIP)
                                        .add(": Switches the tour-chat on and off. Its on by default. And you can type with /g <text> in normal chat when in tour-chat."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.USER)))
                                    .executes(context -> doCommand(context.getSource(),"chat",null)))
                .then(HelpfulLiteralBuilder.literal("info")
                                .withHelpText("Set information about your tour.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour info <infotext>",McmeColors.TOOLTIP)
                                        .add(": Lets you set an infotext for the tour. Which will then be broadcasted on discord and seen when doing /tour check."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("description",greedyString())
                                        .executes(context -> doCommand(context.getSource(),"info",context.getArgument("description",String.class)))))
                .then(HelpfulLiteralBuilder.literal("announce")
                                .withHelpText("Announce your tour.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour announce <discordroles>",McmeColors.TOOLTIP)
                                        .add(": Announces the tour ingame and on discord. Roles need to be seperated by a comma without a space."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("discordroles",greedyString())
                                        .executes(context -> doCommand(context.getSource(),"announce",context.getArgument("discordroles",String.class)))))
                .then(HelpfulLiteralBuilder.literal("host")
                                .withHelpText("Set another player as the host of your tour.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour host <playername>",McmeColors.TOOLTIP)
                                        .add(": Changes the host from you to another Guide or tour-badge holder."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPermissionArgument())
                                        .executes(context -> doCommand(context.getSource(),"host",context.getArgument("playername",String.class)))))
                .then(HelpfulLiteralBuilder.literal("cohost")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour cohost add <playername>",McmeColors.TOOLTIP)
                                        .add(": Adds a cohost to your tour. /tour cohost remove <playername>: Removes a cohost from your tour. A co-host is a host with less permissions, who can use /ttpa and /tour refreshments."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                .then(HelpfulLiteralBuilder.literal("remove")
                                        .withHelpText("Removes a player as a cohost of your tour.")
                                        .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                                .executes(context -> doCommand(context.getSource(),"cohostRemove",context.getArgument("playername",String.class)))))
                                .then(HelpfulLiteralBuilder.literal("add")
                                        .withHelpText("Set another player as a cohost of your tour.")
                                        .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                            .executes(context -> doCommand(context.getSource(),"cohostAdd",context.getArgument("playername",String.class))))))
                .then(HelpfulLiteralBuilder.literal("glow")
                                .withHelpText("Let the cohosts and the host glow.")
                                .withTooltip(getPlugin().createMessage(new MessageStyle(McmeColors.TOOLTIP_STRESSED))
                                        .add("/tour glow",McmeColors.TOOLTIP)
                                        .add(": Toggles the host and co-host glow. Toggle it OFF before switching world."))
                                .requires(sender -> (PluginData.hasPermission(sender, Permission.HOST)))
                                    .executes(context -> doCommand(context.getSource(),"glow",null)));
        return helpfulLiteralBuilder;
    }

    private int doCommand(McmeCommandSender sender, String command, String arg){
        Tour tour;
        McmeProxyPlayer player = (McmeProxyPlayer) sender;
        switch (command) {
            case "request" -> TourRequest.informHosts(player, arg);
            case "join" -> {
                tour = PluginData.getTour(arg);
                if (tour != null) tour.addPlayer(player);
            }
            case "leave" -> {
                if (!PluginData.isInTour(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if (tour != null) tour.removePlayer(player);
            } case "start" -> {
                tour = new Tour(player, arg);
                PluginData.addTour(tour);
PluginData.getTours().forEach((name -> {
    Logger.getGlobal().info("Tour: "+name);
    Logger.getGlobal().info("Host: "+PluginData.getTour(name).getHost());
}));
            }
            case "end" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if (tour != null) tour.endTour();
            }
            case "hat" -> TourHat.handle(player.getName(), player.getServerInfo().getName());
            case "kick" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if (tour != null) tour.kickPlayer(McmeTours.getProxy().getPlayer(arg));
            }
            case "refreshments" -> {
                if (!PluginData.isCoHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if (tour != null) tour.giveRefreshments();
            }
            case "list" -> {
                if (!PluginData.isInTour(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.tourList(player);
            } case "chat" -> {
                if (!PluginData.isInTour(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                /*
                tour = PluginData.getTour((TourCommandSender) sender);
                tour.activateTourChat((ProxiedPlayer) ((TourCommandSender)sender).getCommandSender());
                */
                PluginData.getMessageUtil().sendErrorMessage(sender, "This command is deactivated due to a bungee issue. Please use /g <message> or /l <message> to write outside of tour chat.");
            }
            case "info" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.setInfoText(arg);
            }
            case "announce" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.sendDAnnouncement(arg);
            }
            case "host" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.setHost(McmeTours.getProxy().getPlayer(arg));
            }
            case "cohostAdd" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.setCoHost(McmeTours.getProxy().getPlayer(arg));
            }
            case "cohostRemove" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.removeCoHost(McmeTours.getProxy().getPlayer(arg));
            }
            case "glow" -> {
                if (!PluginData.isHost(player)) {
                    PluginData.getMessageUtil().sendNotPossible(sender);
                    break;
                }
                tour = PluginData.getTour(player);
                if(tour!=null) tour.switchGlow();
            } case "check" -> TourCheck.sendInfos(sender);
        }
        return 0;
    }
}