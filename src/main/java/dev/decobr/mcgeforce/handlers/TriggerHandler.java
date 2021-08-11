package dev.decobr.mcgeforce.handlers;

import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import dev.sllcoding.mcgeforce.data.HighlightType;

public interface TriggerHandler {
    boolean checkAll(String message);

    boolean isMessageFromPlayer();
    boolean checkStartGame();
    boolean checkWin();
    boolean checkKill();
    boolean checkDeath();

    default void createHighlight(HighlightType type, int start, int end) {
        MCGeForceHelper.INSTANCE.saveHighlight(type, start, end);
    }
}
