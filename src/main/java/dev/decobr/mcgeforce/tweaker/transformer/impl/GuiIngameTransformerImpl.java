package dev.decobr.mcgeforce.tweaker.transformer.impl;

import dev.decobr.mcgeforce.MCGeForce;
import dev.decobr.mcgeforce.handlers.TriggerHandler;
import net.minecraft.util.EnumChatFormatting;

public class GuiIngameTransformerImpl {

    public static void checkTitle(String title) {
        if (title == null || title.equals("")) return;

        String unformattedTitle = EnumChatFormatting.getTextWithoutFormattingCodes(title);

        for (TriggerHandler triggerHandler : MCGeForce.getInstance().getTriggerHandlers()) triggerHandler.onTitle(unformattedTitle);
    }

}
