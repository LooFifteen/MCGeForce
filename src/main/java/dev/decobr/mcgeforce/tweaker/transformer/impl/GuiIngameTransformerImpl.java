package dev.decobr.mcgeforce.tweaker.transformer.impl;

import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import dev.decobr.mcgeforce.handlers.impl.HypixelTriggerHandler;
import dev.sllcoding.mcgeforce.data.HighlightType;
import net.minecraft.util.EnumChatFormatting;

public class GuiIngameTransformerImpl {
    public static void checkTitle(String title) {
        if (title == null || title.equals("")) return;

        String lowercaseTitle = EnumChatFormatting.getTextWithoutFormattingCodes(title.toLowerCase());

        long startTime = HypixelTriggerHandler.INSTANCE.startGameTime;
        int endTime = (int) (HypixelTriggerHandler.INSTANCE.startGameTime - System.currentTimeMillis());

        if (startTime != 0) {
            if (lowercaseTitle.contains("game over")) {
                MCGeForceHelper.INSTANCE.saveHighlight(HighlightType.GAME, endTime, 1000);
            } else if ((lowercaseTitle.contains("you won") || lowercaseTitle.startsWith("victory"))) {
                MCGeForceHelper.INSTANCE.saveHighlight(HighlightType.WIN, endTime, 1000);
            } else {
                return;
            }

            HypixelTriggerHandler.INSTANCE.startGameTime = 0;
        }
    }
}
