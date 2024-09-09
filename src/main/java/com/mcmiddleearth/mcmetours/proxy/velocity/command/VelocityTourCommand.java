package com.mcmiddleearth.mcmetours.proxy.velocity.command;

import com.mcmiddleearth.base.core.command.SimpleTabCompleteRequest;
import com.mcmiddleearth.base.core.command.TabCompleteRequest;
import com.mcmiddleearth.base.core.command.handler.AbstractCommandHandler;
import com.mcmiddleearth.base.velocity.command.VelocityMcmeCommandSender;
import com.velocitypowered.api.command.SimpleCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Eriol_Eandur
 */

public class VelocityTourCommand implements SimpleCommand {

    private final String permission;
    private final AbstractCommandHandler handler;

    public VelocityTourCommand(AbstractCommandHandler handler, String permission) {
        this.handler = handler;
        this.permission = permission;
    }

    @Override
    public void execute(Invocation invocation) {
        handler.execute(new VelocityMcmeCommandSender(invocation.source()),invocation.arguments());
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.supplyAsync(()->{
            TabCompleteRequest request = new SimpleTabCompleteRequest(new VelocityMcmeCommandSender(invocation.source()),
                                   String.format("/%s %s",invocation.alias(),String.join(" ",invocation.arguments())));
            handler.onTabComplete(request);
            return request.getSuggestions();
        });
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return permission==null || invocation.source().hasPermission(permission);
    }
}
