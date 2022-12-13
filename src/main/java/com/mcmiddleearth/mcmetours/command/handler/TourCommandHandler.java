package com.mcmiddleearth.mcmetours.command.handler;

import com.mcmiddleearth.command.AbstractCommandHandler;
import com.mcmiddleearth.command.McmeCommandSender;
import com.mcmiddleearth.command.builder.HelpfulLiteralBuilder;
import com.mcmiddleearth.command.builder.HelpfulRequiredArgumentBuilder;
import com.mcmiddleearth.mcmetours.command.TourCommandSender;
import com.mcmiddleearth.mcmetours.command.arguments.CommandHelpArgument;
import com.mcmiddleearth.mcmetours.command.arguments.CommandHostArgument;
import com.mcmiddleearth.mcmetours.command.arguments.CommandPlayerArgument;
import com.mcmiddleearth.mcmetours.data.Permission;
import com.mcmiddleearth.mcmetours.data.PluginData;
import com.mcmiddleearth.mcmetours.tour.Tour;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
                        .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER))
                            .executes(context -> doCommand(context.getSource(),"request", null)))
                .then(HelpfulLiteralBuilder.literal("join")
                                .withHelpText("")
                                .withTooltip("")
                                .then(HelpfulRequiredArgumentBuilder.argument("hostname",new CommandHostArgument())
                                        .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER))
                                                .executes(context -> doCommand(context.getSource(),"join",context.getArgument("hostname",String.class)))))
                .then(HelpfulLiteralBuilder.literal("leave")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER))
                                    .executes(context -> doCommand(context.getSource(),"leave", null)))
                .then(HelpfulLiteralBuilder.literal("help")
                                .withHelpText("")
                                .withTooltip("")
                                .then(HelpfulRequiredArgumentBuilder.argument("helpText",new CommandHelpArgument())
                                    .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER))
                                        .executes(context -> doCommand(context.getSource(),"help",context.getArgument("helpText",String.class)))))
                .then(HelpfulLiteralBuilder.literal("start")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"start",null)))
                .then(HelpfulLiteralBuilder.literal("end")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"end",null)))
                .then(HelpfulLiteralBuilder.literal("hat")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"hat",null)))
                .then(HelpfulLiteralBuilder.literal("kick")
                                .withHelpText("")
                                .withTooltip("")
                                    .then(HelpfulRequiredArgumentBuilder.argument("player",new CommandPlayerArgument()))
                                    .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                        .executes(context -> doCommand(context.getSource(),"kick",context.getArgument("player",String.class))))
                .then(HelpfulLiteralBuilder.literal("refreshments")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"refreshments",null)))
                .then(HelpfulLiteralBuilder.literal("list")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.HOST))
                                    .executes(context -> doCommand(context.getSource(),"list",null)))
                .then(HelpfulLiteralBuilder.literal("chat")
                                .withHelpText("")
                                .withTooltip("")
                                .requires(sender -> PluginData.hasPermission((TourCommandSender) sender, Permission.USER))
                                    .executes(context -> doCommand(context.getSource(),"chat",null)));

                /*
                .then(HelpfulLiteralBuilder.literal("request")
                                .then(HelpfulRequiredArgumentBuilder.argument("player",new CommandTestArgument()))
                                .executes(context -> doCommand(context.getSource(),"request", context.getInput())));

                 */





        return helpfulLiteralBuilder;
    }

    private int doCommand(McmeCommandSender sender, String command, String arg){
        ProxiedPlayer player = (ProxiedPlayer) ((TourCommandSender)sender).getCommandSender();
        switch (command) {
            case "request":
                break;
            case "join":
                PluginData.getMessageUtil().sendInfoMessage(sender,arg);
                break;
            case "leave":
                break;
            case "help":
                break;
            case "start":
                Tour tour = new Tour(player);
                PluginData.addTour(tour);
                break;
            case "end":
                break;
            case "hat":
                break;
            case "kick":
                break;
            case "refreshments":
                break;
            case "list":
                break;
            case "chat":
                break;
        }
        return 0;
    }

}
