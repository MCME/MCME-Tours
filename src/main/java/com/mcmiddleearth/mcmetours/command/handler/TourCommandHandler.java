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
import com.mcmiddleearth.mcmetours.util.Style;
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
                                .withHelpText("Submit a request for a tour.")
                                .withTooltip(Style.HIGHLIGHT+"/tour request <topic>"+ Style.HIGHLIGHT_STRESSED
                                        +": Sends a tour request to all guides and people with tour-badge on the server and the discord. The topic is optional and the request can be send without.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && !PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"request", null))
                                        .then(HelpfulRequiredArgumentBuilder.argument("topic",greedyString())
                                            .executes(context -> doCommand(context.getSource(),"request",context.getArgument("topic",String.class)))))
                .then(HelpfulLiteralBuilder.literal("check")
                                .withHelpText("List all current tours.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender,Permission.USER) && !PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"check",null)))
                .then(HelpfulLiteralBuilder.literal("join")
                                .withHelpText("Join one of the running tours.")
                                .withTooltip(Style.HIGHLIGHT+"/tour join <name of tour>"+Style.HIGHLIGHT_STRESSED
                                        +": Joins the specific tour you want to join.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.tourRunning() && !PluginData.isInTour((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("tourname",new CommandTournameArgument())
                                                .executes(context -> doCommand(context.getSource(),"join",context.getArgument("tourname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("leave")
                                .withHelpText("Leave the currently joined tour.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"leave", null)))
                .then(HelpfulLiteralBuilder.literal("start")
                                .withHelpText("Start a new tour.")
                                .withTooltip(Style.HIGHLIGHT+"/tour start <name of tour>"+Style.HIGHLIGHT_STRESSED
                                        +": Starts a tour with a set name. The name can only be one word.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && !PluginData.isInTour((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("tourname",word())
                                        .executes(context -> doCommand(context.getSource(),"start",context.getArgument("tourname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("end")
                                .withHelpText("End your current tour.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"end",null)))
                .then(HelpfulLiteralBuilder.literal("hat")
                                .withHelpText("Set an item as your hat.")
                                .withTooltip(Style.HIGHLIGHT+"/tour hat"+Style.HIGHLIGHT_STRESSED
                                        +": Sets the item in your main hand as your hat. It can be used outside of tours.")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"hat",null)))
                .then(HelpfulLiteralBuilder.literal("kick")
                                .withHelpText("Kick a player from your tour.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPlayerArgument())
                                            .executes(context -> doCommand(context.getSource(),"kick",context.getArgument("playername",String.class)))))
                .then(HelpfulLiteralBuilder.literal("refreshments")
                                .withHelpText("Give all participants refreshments.")
                                .withTooltip(Style.HIGHLIGHT+"/tour refreshments"+Style.HIGHLIGHT_STRESSED+": Gives all tourists tea and cookies.")
                                .requires(sender ->  PluginData.isCoHost((TourCommandSender) sender))
                                    .executes(context -> doCommand(context.getSource(),"refreshments",null)))
                .then(HelpfulLiteralBuilder.literal("list")
                                .withHelpText("List all participants of your tour.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"list",null)))
                .then(HelpfulLiteralBuilder.literal("chat")
                                .withHelpText("Switch the tour-chat on and off.")
                                .withTooltip(Style.HIGHLIGHT+"/tour chat"+Style.HIGHLIGHT_STRESSED
                                        +": Switches the tour-chat on and off. Its on by default. And you can type with /g <text> in normal chat when in tour-chat.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.USER) && PluginData.isInTour((TourCommandSender) sender)))
                                    .executes(context -> doCommand(context.getSource(),"chat",null)))
                .then(HelpfulLiteralBuilder.literal("info")
                                .withHelpText("Set information about your tour.")
                                .withTooltip(Style.HIGHLIGHT+"/tour info <infotext>"+Style.HIGHLIGHT_STRESSED
                                        +": Lets you set an infotext for the tour. Which will then be broadcasted on discord and seen when doing /tour check.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("description",greedyString())
                                        .executes(context -> doCommand(context.getSource(),"info",context.getArgument("description",String.class)))))
                .then(HelpfulLiteralBuilder.literal("announce")
                                .withHelpText("Announce your tour.")
                                .withTooltip(Style.HIGHLIGHT+"/tour announce <discordroles>"+Style.HIGHLIGHT_STRESSED
                                        +": Announces the tour ingame and on discord. Roles need to be seperated by a comma without a space.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("discordroles",greedyString())
                                        .executes(context -> doCommand(context.getSource(),"announce",context.getArgument("discordroles",String.class)))))
                .then(HelpfulLiteralBuilder.literal("host")
                                .withHelpText("Set another player as the host of your tour.")
                                .withTooltip(Style.HIGHLIGHT+"/tour host <playername>"+Style.HIGHLIGHT_STRESSED
                                        +": Changes the host from you to another Guide or tour-badge holder.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
                                    .then(HelpfulRequiredArgumentBuilder.argument("playername",new CommandPermissionArgument())
                                        .executes(context -> doCommand(context.getSource(),"host",context.getArgument("playername",String.class)))))
                .then(HelpfulLiteralBuilder.literal("cohost")
                                .withTooltip(Style.HIGHLIGHT+"/tour cohost add <playername>"+Style.HIGHLIGHT_STRESSED
                                        +": Adds a cohost to your tour. /tour cohost remove <playername>: Removes a cohost from your tour. A co-host is a host with less permissions, who can use /ttpa and /tour refreshments.")
                                .requires(sender -> (PluginData.hasPermission((TourCommandSender) sender, Permission.HOST) && PluginData.isHost((TourCommandSender) sender)))
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
                                .withTooltip(Style.HIGHLIGHT+"/tour glow"+Style.HIGHLIGHT_STRESSED
                                        +": Toggles the host and co-host glow. Toggle it OFF before switching world.")
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
                tour.tourList((ProxiedPlayer) ((TourCommandSender) sender).getCommandSender());
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