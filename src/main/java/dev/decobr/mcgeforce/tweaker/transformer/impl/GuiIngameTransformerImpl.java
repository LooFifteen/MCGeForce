package dev.decobr.mcgeforce.tweaker.transformer.impl;

import dev.decobr.mcgeforce.MCGeForce;
import dev.decobr.mcgeforce.handlers.TriggerHandler;
import net.minecraft.util.EnumChatFormatting;

public class GuiIngameTransformerImpl {

    public static void checkTitle(String title) {
        if (title == null || title.equals("") || !MCGeForce.getInstance().getConfig().isEnabled()) return;

        String unformattedTitle = EnumChatFormatting.getTextWithoutFormattingCodes(title);

        for (TriggerHandler triggerHandler : MCGeForce.getInstance().getTriggerHandlers()) if (triggerHandler.isEnabled()) if (triggerHandler.onTitle(unformattedTitle)) break;
    }

}
