package dev.decobr.mcgeforce.handlers.impl;

import dev.decobr.mcgeforce.MCGeForce;
import dev.decobr.mcgeforce.bindings.MCGeForceHelper;
import dev.decobr.mcgeforce.handlers.TriggerHandler;
import dev.sllcoding.mcgeforce.data.HighlightType;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.Minecraft;

public class VanillaTriggerHandler implements TriggerHandler {

    @Override
    public boolean isEnabled() {
        return MCGeForce.getInstance().getConfig().isVanillaEnabled() && !EssentialAPI.getMinecraftUtil().isHypixel();
    }

    @Override
    public boolean onMessage(String message) {
        if(Minecraft.getMinecraft().thePlayer == null) return false;

        String name = Minecraft.getMinecraft().thePlayer.getName();
        if (MCGeForce.getInstance().getConfig().isVanillaDeathsEnabled() && message.matches(name + " was|walked into|drowned|experienced kinetic energy|hit the ground too hard|fell|went|burned to death|tried to swim in lava|discovered the floor was lava|froze to death|starved to death|suffocated in a wall|didn't want to live in the same world|withered away|died|swam in lava")) {
            MCGeForceHelper.saveHighlight(HighlightType.DEATH);
            return true;
        } else if (MCGeForce.getInstance().getConfig().isVanillaKillsEnabled() && message.matches("(by|whilst trying to escape|whilst fighting|due to|skull from|trying to hurt|in the same world as|because of) " + name)) {
            MCGeForceHelper.saveHighlight(HighlightType.KILL);
            return true;
        }

        return false;
    }

    @Override
    public boolean onTitle(String title) {
        return false;
    }

}
