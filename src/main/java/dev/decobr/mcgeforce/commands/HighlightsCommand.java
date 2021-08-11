package dev.decobr.mcgeforce.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class HighlightsCommand extends CommandBase {
    public String getCommandName() {
        return "highlights";
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/highlights";
    }

    public void processCommand(ICommandSender sender, String[] args) {

    }

    public int getRequiredPermissionLevel() {
        return -1;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
